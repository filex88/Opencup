set "JAVA_HOME=C:\liferay-portal-6.2-ce-ga2\jdk1.7.0_67"

if exist "%CATALINA_HOME%/jre1.6.0_20/win" (
	if not "%JAVA_HOME%" == "" (
		set JAVA_HOME=
	)

	set "JRE_HOME=%CATALINA_HOME%/jre1.6.0_20/win"
)

set "CATALINA_OPTS=%CATALINA_OPTS% -Dfile.encoding=UTF8 -Djava.net.preferIPv4Stack=true  -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -Duser.timezone=GMT -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -Dexternal-properties=portal-developer.properties"