package net.geoprism.registry;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.runwaysdk.resource.ClasspathResource;

public class DuplicateControllerMethodChecker
{
  public static void main(String[] args) throws Exception
  {
    checkPackages("com/runwaysdk", "net/geoprism");
  }
  
  public static void checkPackages(String ...packs) throws Exception
  {
    for (String pack : packs)
    {
      checkPackage(pack);
    }
  }
  
  public static void checkPackage(String pack) throws Exception
  {
    List<ClasspathResource> resources = ClasspathResource.getResourcesInPackage(pack);
    
    for (ClasspathResource resource : resources)
    {
      if (resource.isPackage())
      {
        checkPackage(resource.getAbsolutePath());
      }
      else if (resource.getNameExtension().equals("class"))
      {
        String clazzName = resource.getAbsolutePath().replace("/", ".").replace(".class", "");
        
        Class<?> clazz = DuplicateControllerMethodChecker.class.getClassLoader().loadClass(clazzName);
        
        if (clazz.getAnnotation(com.runwaysdk.mvc.Controller.class) != null)
        {
          System.out.println("Checking class [" + clazz.getName() + "].");
          
          Method[] methods = clazz.getMethods();
          
          Map<String, Method> map = new HashMap<String, Method>();
          
          for (Method method : methods)
          {
            if (map.containsKey(method.getName()))
            {
              throw new RuntimeException(clazz.getName() + " : " + method.getName());
            }
            else if (method.getAnnotation(com.runwaysdk.mvc.Endpoint.class) != null)
            {
              map.put(method.getName(), method);
            }
          }
        }
      }
    }
  }
}
