var  mgr = new Packages.javax.script.ScriptEngineManager();
var factories = mgr.getEngineFactories();

for (var factory in Iterator(factories))
{
	Packages.java.lang.System.out.println(factory.getEngineName());
	Packages.java.lang.System.out.println(factory.getEngineVersion());
}