package snowflake

import (
	"gorm.io/gorm"
	"math/rand"
	"time"
)

// RandWorkerId 随机生成WorkerId
func RandWorkerId() (workerId int64) {
	rand.Seed(time.Now().Unix())
	workerId = rand.Int63n(workerIdMax)
	return
}

// GetWorkerId 通过DB生成WorkerId
func GetWorkerId(conn *gorm.DB, namespace string) (workerId int64) {

	wId := QueryWorkerId(conn, namespace)
	if wId <= 0 {
		wId = QueryMaxWorkerId(conn, namespace)
		wId++
		InsertWorkerId(conn, namespace, wId)
	}

	if wId < workerIdMin || int64(wId) > workerIdMax {

	}
	return int64(wId)

}
