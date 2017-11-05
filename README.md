# gradle_maven_nexus3

## 0x00 Nexus3配合Gradle搭建私有仓库

场景：将自己的代码通过gradle上传到使用nexus3搭建的私有仓库。

## 0x01 Nexus配置

### `docker`

- 安装 `docker` 和 `kitmatic`
- 安装 `nexus3`

    `kitematic` 搜索 `nexus3`，选择 `Sonatype nexus3(Sonatype Nexus Repository Manager3)`

- 配置
    
    - 配置端口号
    - 配置存储目录

### `nexus3`

- 默认管理员：admin:admin123
- 修改管理员密码
    
    依次点击：`右上角admin` > `Change password`
    
- 关闭匿名用户访问权限

    依次点击：`顶部设置` > `Administration` > `Security` > `Anonymous` > `Allow anonymous users to access the server` 
    
- 创建仓库
        
   `Administration` > `Repository` > `Repositories` > `Create repository` > `maven2(hosted)` > `ttdevs-releases`
   
   > `group`, `hosted`, `proxy`

- 创建角色

    `Administration` > `Security` > `Roles` > `Nexus role`
    
    - uploader

        - `nx-repository-admin-maven2-ttdevs-release-*`
        - `nx-repository-view-maven2-ttdevs-release-*`
        
    - reader
        
        - `nx-repository-admin-maven2-ttdevs-release-browse`
        - `nx-repository-admin-maven2-ttdevs-release-read`
        - `nx-repository-view-maven2-ttdevs-release-browse`
        - `nx-repository-view-maven2-ttdevs-release-read`

- 创建用户
    
    `Administration` > `Security` > `Users` > `Create user`
     
    - Uploader

        赋予上步创建的 `uploader` 权限
        
    - Reader

        赋予上步创建的 `reader` 权限
 

## 0x02 Android Library
    
- 创建Project：`AndroidLibrary`
- 创建Module：`log`，编写业务和测试代码
    
    > 如果此处有引用第三方代码，建议不要在后面添加 `@aar` !!!
    
- 创建 `config.gradle`
    
    ``` gradle
    ext {
        nexusConfig = [
                repository: 'http://ttdevs.vicp.cc:9020/repository/ttdevs-releases/'
        ]
    }
    ```

- 修改 `gradle.properties`
    
    ``` config
    nexusUploader=Uploader
    nexusUploaderPwd=your_password
    ```
    
    > 为了安全起见，上传的权限不放入git仓库
    
- 修改 `build.gradle`，添加 `apply from: 'config.gradle'`
- 创建 `publish.gradle`
    
    ``` gradle
    apply plugin: 'maven'
    
    uploadArchives {
        configuration = configurations.archives
        repositories {
            mavenDeployer {
                // snapshotRepository
                repository(url: nexusConfig.repository) {
                    authentication(userName: nexusUploader, password: nexusUploaderPwd)
                }
    
                pom.project {
                    version project.version
                    artifactId project.name
                    groupId 'com.ttdevs.lib'
                    packaging 'aar'
                    description project.description
                }
            }
        }
    
        task androidSourcesJar(type: Jar) {
            classifier = 'sources'
            from android.sourceSets.main.java.sourceFiles
        }
    
        artifacts {
            archives androidSourcesJar
        }
    }
    ```
    
- 修改 `log/build.gradle`
    
    ``` gradle
    ext {
        project.version = '0.1.0'
        project.description = 'ttdevs log'
    }
    
    apply from: getRootDir().getAbsolutePath() + '/publish.gradle'
    ```

- 上传

    `./gradlew :log:uploadArchives`
    
    ``` shell
    ➜  AndroidLibrary ./gradlew :log:uploadArchives
    Could not find metadata com.ttdevs.lib:log/maven-metadata.xml in remote (http://ttdevs.vicp.cc:9020/repository/ttdevs-releases/)
    
    BUILD SUCCESSFUL in 3s
    26 actionable tasks: 1 executed, 25 up-to-date
    ➜  AndroidLibrary 
    ```

## 0x03 Android Demo

- 创建Project：`AndroidDemo`
- 修改 `AndroidDemo/build.gradle`

``` gradle
...

allprojects {
    repositories {
        jcenter()

        maven {
            url "http://ttdevs.vicp.cc:9020/repository/ttdevs-releases/"
            credentials {
                username 'Reader'
                password 'ttdevs'
            }
        }
    }
}

...
```

- 添加引用

   修改 `app/build.gradle` 添加引用：
   
   `implementation 'com.ttdevs.lib:log:0.1.0'`
   

## 0xFF 参考

- [搭建私有-Sonatype-仓库][1]

---
[1]: http://blog.bugtags.com/2016/01/27/embrace-android-studio-maven-deploy/#搭建私有-Sonatype-仓库

    




