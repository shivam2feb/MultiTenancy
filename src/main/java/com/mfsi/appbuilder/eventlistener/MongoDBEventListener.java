//package com.mfsi.appbuilder.eventlistener;
//
//import java.time.LocalDateTime;
//
//import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
//import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
//import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
//import org.springframework.stereotype.Component;
//
//import com.mfsi.appbuilder.document.Project;
//
//@Component
//public class MongoDBEventListener extends AbstractMongoEventListener<Project>{
//
//	@Override
//	public void onBeforeConvert(BeforeConvertEvent<Project> beforeConvertEvent) {
//		beforeConvertEvent.getSource().setLastUpdatedOn(LocalDateTime.now());
//	}
//
//}
