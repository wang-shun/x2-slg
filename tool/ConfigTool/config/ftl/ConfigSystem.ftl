package ${package1};
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.google.common.base.Strings;
import com.xgame.utils.StringUtil;
import com.xgame.utils.ClassUtil;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;


/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date ${xdate} 
 */
@Slf4j
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConfigSystem {
	
	@Value(${r'"${xgame.config.path}"'})
	public String path;
		
	@PostConstruct
	public void start(){
		readFile(new File(path));
	}
	
	private void readFile(File file){
		try {
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(int i=0;i<files.length;i++){
					readFile(files[i]);
				}
			}else{
				parse(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parse(File file) {
		try {
			String pirName = file.getName().replace(".txt", "");
			String className = toClassName(pirName)+"PirFactory";
			Map<String, Class<BasePriFactory>> subClasses = ClassUtil.getSubClasses("com.xgame.config", BasePriFactory.class);
			Class<BasePriFactory> priClass = subClasses.get(className);
			BasePriFactory	priFactory = (BasePriFactory)priClass.getMethod("getInstance").invoke(priClass);
			if(priFactory==null){
				log.error(" config not find : "+file.getCanonicalPath());
				System.exit(-1);
				return;
			}
			log.info(" load config "+file.getCanonicalPath());
			List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			String[] fields = lines.get(1).split("\t");
			String[] fieldTypes = lines.get(2).split("\t");
			for(int i=3;i<lines.size();i++){
				Object key = null;
				String [] values = lines.get(i).split("\t");
				BaseFilePri newPri = (BaseFilePri) priFactory.newPri();
				for(int j=0;j<fields.length;j++){
					if(fields[j].equals("#"))continue;
					String field = (j<fields.length?fields[j]:"").trim();
					String fieldType = (j<fieldTypes.length?fieldTypes[j]:"").trim();
					String value = (j<values.length?values[j]:"".trim());
					if(value.equals("")||value==null)continue;
					Field javaField = newPri.getClass().getDeclaredField(field);
					if(javaField==null){
						log.error(" field not find, fieldName: "+field);
						System.exit(-1);
					}
					javaField.setAccessible(true);
					Object setValue = convert(fieldType, value);
					javaField.set(newPri, setValue);
					if(j==0)key = setValue;
				}
				if(key==null){
					log.error(" mainKey not find, ClassName: "+newPri.getClass().getName());
					System.exit(-1);
				}
				priFactory.pares(newPri);
				priFactory.init();
				priFactory.factory.put(key, newPri);
			}
		} catch (Exception e) {
			log.error(StringUtil.getExceptionTrace(e));
			System.exit(-1);
		}
	}
	
	public Object convert(String type, String value) {
		switch (type) {
		case "int":{
			if(value.equals("")){
				return 0;
			}else{
				return Integer.valueOf(value);
			}
		}
		case "str":
			return String.valueOf(value);
		case "f":
			if(value.equals("")){
				return 0f;
			}else{
				return Integer.valueOf(value);
			}
		}
		return value;
	}
	
	public String toClassName(String className){
		return className.substring(0,1).toUpperCase()+className.substring(1);
	}
	
}