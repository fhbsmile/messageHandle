/** 
 * Project Name:messageHandle 
 * File Name:Handle.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles 
 * Date:2016年5月13日上午9:15:31
 * version:v1.0
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */ 


/** 
 * Project Name:messageHandle 
 * File Name:Handle.java 
 * Package Name:com.tsystems.si.aviation.imf.messageHandle.handles 
 * Date:2016年5月13日上午9:15:31 
 * Copyright (c) 2016, Bolo.Fang@t-systems.com All Rights Reserved. 
 * 
 */  
package com.tsystems.si.aviation.imf.messageHandle.handles;

import com.tsystems.si.aviation.imf.messageHandle.db.bean.ImfMessage;
import com.tsystems.si.aviation.imf.messageHandle.handles.tools.FlightXmlBean;

/**   
 * @ClassName:  Handle   
 * @Description:TODO
 * @author: Bolo.Fang@t-systems.com  
 * @date:   2016年5月13日 上午9:15:31   
 *      
 */
public interface Handle {
	public void initHandle();
	public ImfMessage createImfMessage(FlightXmlBean fxbean);
	
}
