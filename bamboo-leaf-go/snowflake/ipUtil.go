package snowflake

import (
	"net"

	"github.com/golang/glog"
)

// 获取本机IP
func getHostIp() string {
	addr, err := net.InterfaceAddrs()
	if err != nil {
		glog.Errorf("getHostIp is error,msg:%d", err)
		return ""
	}
	for _, value := range addr {
		if ipNet, ok := value.(*net.IPNet); ok && !ipNet.IP.IsLoopback() {
			if ipNet.IP.To4() != nil {
				return ipNet.IP.String()
			}
		}
	}
	return "127.0.0.1"
}
