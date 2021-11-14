package snowflake

import (
	"fmt"
	"sync"
	"time"

	"github.com/golang/glog"
)

const (
	epoch          = int64(1577808000)                 // 设置起始时间(时间戳/秒)：2020-01-01 00:00:00，有效期69年
	timestampBits  = uint(31)                          // 时间戳占用位数
	workerIdBits   = uint(14)                          // 机器id所占位数
	sequenceBits   = uint(18)                          // 序列所占的位数
	timestampMax   = int64(-1 ^ (-1 << timestampBits)) // 时间戳最大值
	workerIdMax    = int64(-1 ^ (-1 << workerIdBits))  // 支持的最大机器id数量
	workerIdMin    = 1
	sequenceMask   = int64(-1 ^ (-1 << sequenceBits)) // 支持的最大序列id数量
	workerIdShift  = sequenceBits                     // 机器id左移位数
	timestampShift = sequenceBits + workerIdBits      //+ datacenterIdBits // 时间戳左移位数
)

type Snowflake struct {
	sync.Mutex
	timestamp int64
	workerId  int64
	sequence  int64
}

func NewSnowflake(workerId int64) (*Snowflake, error) {
	if workerId < 0 || workerId > workerIdMax {
		return nil, fmt.Errorf("workerId must be between 0 and %d", workerIdMax-1)
	}
	return &Snowflake{
		timestamp: 0,
		workerId:  workerId,
		sequence:  0,
	}, nil
}

func (s *Snowflake) NextVal() uint64 {
	s.Lock()
	now := time.Now().Unix() // 秒
	if s.timestamp == now {
		// 当同一时间戳（精度：秒）下多次生成id会增加序列号
		s.sequence = (s.sequence + 1) & sequenceMask
		if s.sequence == 0 {
			// 如果当前序列超出12bit长度，则需要等待下一秒
			// 下一毫秒将使用sequence:0
			for now <= s.timestamp {
				now = time.Now().Unix()
			}
		}
	} else {
		// 不同时间戳（精度：秒）下直接使用序列号：0
		s.sequence = 0
	}
	t := now - epoch
	if t > timestampMax {
		s.Unlock()
		glog.Errorf("epoch must be between 0 and %d", timestampMax-1)
		return 0
	}
	s.timestamp = now
	r := uint64(t<<timestampShift | (s.workerId << workerIdShift) | (s.sequence))
	s.Unlock()
	return r
}

// GetDeviceID 获取数据中心ID和机器ID
func GetDeviceID(sid int64) (workerId int64) {
	workerId = (sid >> workerIdShift) & workerIdMax
	return
}

// GetTimestamp 获取时间戳
func GetTimestamp(sid int64) (timestamp int64) {
	timestamp = (sid >> timestampShift) & timestampMax
	return
}

// GetGenTimestamp 获取创建ID时的时间戳
func GetGenTimestamp(sid int64) (timestamp int64) {
	timestamp = GetTimestamp(sid) + epoch
	return
}

// GetGenTime 获取创建ID时的时间字符串(精度：秒)
func GetGenTime(sid int64) (t string) {
	// 需将GetGenTimestamp获取的时间戳/1000转换成秒
	t = time.Unix(GetGenTimestamp(sid), 0).Format("2006-01-02 15:04:05")
	return
}

// GetTimestampStatus 获取时间戳已使用的占比：范围（0.0 - 1.0）
func GetTimestampStatus() (state float64) {
	state = float64((time.Now().Unix() - epoch)) / float64(timestampMax)
	return
}
