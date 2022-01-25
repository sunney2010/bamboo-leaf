package segment

import "gorm.io/gorm"

// SegmentId
func SegmentId(conn *gorm.DB, namespace string) (segment *Segment) {
	seg := QuerySegment(conn,namespace)
	if(seg=nil)
	InsertSegment(conn,namespace,2000, 1, 0, 10 )

	return
}
