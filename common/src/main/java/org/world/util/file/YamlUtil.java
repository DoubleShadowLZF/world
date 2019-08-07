package org.world.util.file;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @Description
 */
public class YamlUtil {

	/**
	 * 读取yaml
	 * @param yamlPath
	 * @return
	 * @throws FileNotFoundException
	 * @throws YamlException
	 */
	public static Object getYaml(String yamlPath) throws FileNotFoundException, YamlException {
		YamlReader reader = new YamlReader(new FileReader(yamlPath));
		return reader.read();
	}
}
