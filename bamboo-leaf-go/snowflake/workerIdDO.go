package snowflake

import (
	"fmt"
	"github.com/golang/glog"
	"gorm.io/gorm"
)

type WorkerId struct {
	ID        uint   `gorm:"column:id;primaryKey"`
	Namespace string `gorm:"column:namespace;type:varchar(20)"`
	HostIp    string `gorm:"column:host_ip;not null;type:varchar(20)"`
	WorkerId  uint   `gorm:"column:worker_id;not null"`
	//CreateTime time.Time `gorm:"column:create_time;not null"`
	//UpdateTime time.Time `gorm:"column:update_time;not null"`
	Remark string `gorm:"column:remark;size:255"`
}
type MaxWorkerId struct {
	WorkerId uint
}

// TableName 将默认表面WorkerId 的表名设置为bamboo_leaf_workerId
func (WorkerId) TableName() string {
	return "bamboo_leaf_workerId"
}

// QueryWorkerId 查询WorkerId
func QueryWorkerId(conn *gorm.DB, namespace string) (workerId uint) {
	var worker WorkerId
	hostIp := getHostIp()
	conn.Raw("select id,namespace,host_ip,worker_id from bamboo_leaf_workerId where namespace=? and host_ip=?", namespace, hostIp).Scan(&worker)
	workerId = worker.WorkerId
	return workerId
}

// QueryMaxWorkerId 查询WorkerId的最大值
func QueryMaxWorkerId(conn *gorm.DB, namespace string) (workerId uint) {
	var worker MaxWorkerId
	var result, _ = conn.Raw("select max(worker_id) as WorkerId from bamboo_leaf_workerId where namespace=?", namespace).Rows()
	//workerId = result.Scan(&worker)
	for result.Next() {
		result.Scan(&worker.WorkerId)
	}
	glog.Infof("workerId:%d", worker.WorkerId)
	return worker.WorkerId
}

// InsertWorkerId 初始化数据
func InsertWorkerId(conn *gorm.DB, namespace string, wId uint) {
	hostIp := getHostIp()
	workerId := WorkerId{Namespace: namespace, HostIp: hostIp, WorkerId: wId, Remark: ""}
	//conn.Raw("insert into bamboo_leaf_workerId(namespace,host_ip,worker_id,remark) value(?,?,?,?)", namespace, hostIp, 1, "")
	result := conn.Create(&workerId)
	fmt.Printf("result:%d\n", result)
}
