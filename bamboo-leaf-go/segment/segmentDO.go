package segment

import (
	"fmt"
	"gorm.io/gorm"
)

type Segment struct {
	ID        uint   `gorm:"column:id;primaryKey"`
	Namespace string `gorm:"column:namespace;type:varchar(20)"`
	LeafVal   uint64 `gorm:"column:leaf_val;not null;type:bigint"`
	Step      uint   `gorm:"column:step;type:int,not null"`
	Delta     uint   `gorm:"column:delta;type:int,not null"`
	Remainder uint   `gorm:"column:remainder;type:int,not null"`
	Retry     uint   `gorm:"column:retry;type:int,not null"`
	Version   uint   `gorm:"column:version;type:int,not null"`
	Remark    string `gorm:"column:remark;size:255"`
}

// TableName 将默认表面WorkerId 的表名设置为bamboo_leaf_workerId
func (Segment) TableName() string {
	return "bamboo_leaf_segment"
}

// InsertSegment 初始化数据
func InsertSegment(conn *gorm.DB, namespace string, step, delta, remainder, retry uint) {
	segment := Segment{Namespace: namespace, Step: step, Delta: delta, Remainder: remainder, Retry: retry, Version: 1, Remark: ""}
	result := conn.Create(&segment)
	fmt.Printf("result:%d\n", result)
}

// QuerySegment 查询WorkerId
func QuerySegment(conn *gorm.DB, namespace string) (segment *Segment) {
	conn.Raw("select id,namespace,leaf_val,step,delta,remainder,retry,version,remark from bamboo_leaf_segment where namespace=? ", namespace).Scan(&segment)
	return segment
}

// UpdateSegment 更新记录
func UpdateSegment(conn *gorm.DB, segment *Segment) {
	m1 := map[string]interface{}{
		"leaf_val": gorm.Expr("leaf_val+?", segment.Step),
		"Version":  gorm.Expr("Version  + ?", 1),
	}
	conn.Debug().Where(" namespace=? and version=? and leaf_val=?", segment.Namespace, segment.Version, segment.LeafVal).UpdateColumns(m1)
	//conn.Raw("update bamboo_leaf_segment set leaf_val=?,step=?,version=? where namespace=? and version=? and leaf_val=? ",
	//	segment.LeafVal,segment.Step,segment.Version+1,segment.Namespace,segment.Version,segment.LeafVal).Scan()

}
func ResetSegment() {

}
