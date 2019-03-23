# example-micronaut-crud-gorm
Simple Micronaut example with CRUD restful API and hibernate GORM persistence layer.
Application is implemented in groovy to leverage GORM and groovy AST compile-time AoP in general.


## Todos

### basic stack

- [x] show preferred constructor based dependency injection
- [x] show typical domain ORM with GORM persistence
- [x] show abstract class implementation of GORM services to customize JPA standard methods
- [x] show javax validation on RESTful API endpoints
- [x] show RESTful web api for CRUD operations
- [x] show mixed REST and HTML view with Thymeleaf template engine (dead simple!)
- [ ] show servlet filter usage as controller action AoP 
- [ ] show RxJava in web stack
- [ ] show RxGorm in persistence
- [ ] integration with Travis-CI

### testing
- [x] show spock integration
- [x] show Micronaut addtional test annotations 

### service discovery
- [x] swagger API annotation
- [x] embedded swagger-ui API test support
- [ ] integration with Consul for centralized runtime/bootstrap configuration
- [ ] integration with Consul for service discovery


### full stack with front end UI
- [ ] show SPA pages for CRUD and data grids
- [ ] show webSocket server push for rich web UI    




## Appendix - Swagger API doc generation

The following setup follows official configuration guide: `https://docs.micronaut.io/latest/guide/index.html#openapi`

During setup, in `build.gradle`, make sure the dependencies are marked with proper build scope:
```groovy
// For groovy the scope should be 'compileOnly'
compileOnly "io.micronaut.configuration:micronaut-openapi"
// For java the scope should be 'annotationProcessor'
//annotationProcessor "io.micronaut.configuration:micronaut-openapi"
// Swagger annotation dependency must be in compile scope because the api doc genertion
// takes place during 'groovyCompile 
compile "io.swagger.core.v3:swagger-annotations"
```
With dependencies loaded, the application needs to know where to store the generated api doc (yaml) file.
By default the file is saved at `<app-root-folder>/build/classes/groovy/main/META-INF/swagger/<app-name>-<version>.yml`

During gradle build, the console will output:
```bash
> Task :compileGroovy
Note: Generating OpenAPI Documentation
Note: Writing OpenAPI YAML to destination: file:/.../example-micronaut-crud-gorm/build/classes/groovy/main/META-INF/swagger/hello-world-0.1.yml
```

To expose it as a static web page, it needs to be mapped to a relative url in `application.yml` configuration file.
```yaml
micronaut:
    router:
        static-resources:
            swagger:
                paths: classpath:META-INF/swagger
                mapping: /swagger/**   # map to <contextRoot>/swagger
```

Now this API doc file can be accessed via url of `http://localhost:8080/swagger`.  

## Appendix - Swagger-UI static resource mapping

This configuration assumes the swagger API doc generation setup is done.

Download swagger-ui from https://swagger.io/tools/swagger-ui/download/
Copy `dist` folder to `src/main/resources/public/swagger-ui`.
In `application.yml` file configure static resource mapping for swagger-ui:
```yaml
micronaut:
    router:
        static-resources:
            swagger_ui:
                paths: classpath:public/swagger-ui # => src/main/resources/public
                mapping: /swagger-ui/**
```
Also modify the `src/main/resources/public/swagger-ui/index.html` file relative paths for js and css files:
```html
<!-- ... -->
    <link rel="stylesheet" type="text/css" href="/swagger-ui/swagger-ui.css" >
    <link rel="icon" type="image/png" href="/swagger-ui/favicon-32x32.png" sizes="32x32" />
    <link rel="icon" type="image/png" href="/swagger-ui/favicon-16x16.png" sizes="16x16" />
<!-- ... -->
    <script src="/swagger-ui/swagger-ui-bundle.js"> </script>
    <script src="/swagger-ui/swagger-ui-standalone-preset.js"> </script>
    <script>
    window.onload = function() {
      // Begin Swagger UI call region
      const ui = SwaggerUIBundle({
        // url: "https://petstore.swagger.io/v2/swagger.json",
        url: "/swagger/hello-world-0.1.yml",
        dom_id: '#swagger-ui',
        deepLinking: true,
        presets: [
          SwaggerUIBundle.presets.apis,
          SwaggerUIStandalonePreset
        ],
        plugins: [
          SwaggerUIBundle.plugins.DownloadUrl
        ],
        layout: "StandaloneLayout"
      })
      // End Swagger UI call region

      window.ui = ui
    }
  </script>
<!-- ... -->
```

Now the swagger-ui is available from `http://localhost:8080/swagger-ui`



## License
[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)
