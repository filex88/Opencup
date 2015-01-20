# Opencup

## Istruzioni per l'installazione

1. Scaricare Liferay Maven SDK
http://heanet.dl.sourceforge.net/project/lportal/Liferay%20Portal/6.2.0%20GA1/liferay-portal-maven-6.2.0-ce-ga1-20131101192857659.zip



2. Impostare nel file setting.xml nella cartella .m2 di MAVEN il profilo corretto, come da seguenti indicazioni (il path _C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7_ va adattato alla propria installazione di Liferay):

```html
  <profiles>

	<profile>
      <id>inject-liferay-properties</id>
       <properties>
         <liferay.version>6.2.0</liferay.version>
         <liferay.auto.deploy.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/deploy</liferay.auto.deploy.dir>
         <liferay.app.server.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42</liferay.app.server.dir>
         <liferay.app.server.deploy.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42/webapps</liferay.app.server.deploy.dir>
        <liferay.app.server.lib.global.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42/lib/ext</liferay.app.server.lib.global.dir>
        <liferay.app.server.portal.dir>C:/Lavoro/Java/liferay-portal-6.2-ce-ga2-tomcat7/tomcat-7.0.42/webapps/ROOT</liferay.app.server.portal.dir>
     </properties>
    </profile>
	
  </profiles>

  <activeProfiles>
  	<activeProfile>inject-liferay-properties</activeProfile>
	
  </activeProfiles>
```


## Documento di Set-up
https://docs.google.com/document/d/1PHLyZgyFIcJwNUj99Cj7bCEiOsOeFXUkloy47Rzmcq4/
