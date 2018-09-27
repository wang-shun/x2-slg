package ${package};
	

<#list catalogs as catalog>
<#if catalog.handler??>
import ${catalog.handler};
</#if>
</#list>
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import com.xgame.msglib.XMessage;
import com.xgame.logic.server.layer.process.StatefulCommand;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 引用类列表
 */
@SuppressWarnings("unchecked")
public class MessagePool {

  	/**
     * 消息初始结构缓存  用于内存池用完后  生产消息
     */
    private HashMap<Integer, Class<? extends StatefulCommand<?>>> samplesHandler = new HashMap<>();
    
    private HashMap<Integer, Class<?>> samplesMessage = new HashMap<>();
    
    public MessagePool() {
    	register();
	}
    
    /**
     * 注册处理器 和消息
     * 通过处理器的泛型获得消息
     * 本方法只会在启动服务器的时候每个消息调一次
     * @param handlerClass
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
	public void register(int id,Class<? extends StatefulCommand<?>> handlerClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, ExecutionException, InterruptedException {
    	ParameterizedType superclass = (ParameterizedType)handlerClass.getGenericSuperclass();
    	samplesMessage.put(id, (Class<? extends XMessage>)superclass.getActualTypeArguments()[0]);
    	samplesHandler.put(id, handlerClass);
    }
	
	
	
	public void register(){
		try {
			<#if catalogs??>
			<#list catalogs as catalog>
			register(${catalog.id},${catalog.name}Handler.class);
			</#list>
			</#if>
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public StatefulCommand<?> getSamplesHandler(int id) {
		try {
			return samplesHandler.get(id).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public Class<?> getSamplesMessage(int id) {
		try {
			return samplesMessage.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	public HashMap<Integer, Class<?>> getSamplesMessage() {
		return samplesMessage;
	}

	public void setSamplesMessage(
			HashMap<Integer, Class<?>> samplesMessage) {
		this.samplesMessage = samplesMessage;
	}
}
