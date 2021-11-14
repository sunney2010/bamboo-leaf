package main

import (
	"flag"
	"fmt"
	"github.com/golang/glog"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"

	"gitee.com/sunney/bamboo-leaf/bamboo-leaf-go/snowflake"
)

func main() {
	flag.Parse()
	defer glog.Flush()
	dataSource := GetDataSource()
	workerId := snowflake.GetWorkerId(dataSource, "zhuzhi168")
	glog.Infof("workerId:%d", workerId)
	worker, _ := snowflake.NewSnowflake(workerId)
	for i := 1; i <= 100; i++ {
		snowflakeId := worker.NextVal()
		genTime:= snowflake.GetGenTime(int64(snowflakeId))
		wId:= snowflake.GetDeviceID(int64(snowflakeId))
		fmt.Printf("snowflakeId:%d\n", snowflakeId)
		fmt.Printf("genTime:"+ genTime+"\n")
		fmt.Printf("wId:%d\n", wId)

		//glog.Infof("snowflakeId:%d", snowflakeId)
		//glog.Infof("genTime:%d", genTime)
		//glog.Infof("wId:%d", wId)
	}
}

//参数含义:数据库用户名、密码、主机ip、连接的数据库、端口号
func dbConn(User, Password, Host, Db string, Port int) *gorm.DB {
	connArgs := fmt.Sprintf("%s:%s@(%s:%d)/%s?charset=utf8&parseTime=True&loc=Local", User, Password, Host, Port, Db)
	db, _ := gorm.Open(mysql.New(mysql.Config{
		DSN:                       connArgs, // DSN data source name
		DefaultStringSize:         256,      // string 类型字段的默认长度
		DisableDatetimePrecision:  true,     // 禁用 datetime 精度，MySQL 5.6 之前的数据库不支持
		DontSupportRenameIndex:    true,     // 重命名索引时采用删除并新建的方式，MySQL 5.7 之前的数据库和 MariaDB 不支持重命名索引
		DontSupportRenameColumn:   true,     // 用 `change` 重命名列，MySQL 8 之前的数据库和 MariaDB 不支持重命名列
		SkipInitializeWithVersion: false,    // 根据当前 MySQL 版本自动配置
	}), &gorm.Config{})
	return db
}

func GetDataSource() (conn *gorm.DB) {
	for {
		conn = dbConn("root", "Zhuzhi2020!", "www.zhuzhi.vip", "bamboo-leaf", 3306)
		if conn != nil {
			break
		}
		fmt.Println("本次未获取到mysql连接")
	}
	return conn
}
