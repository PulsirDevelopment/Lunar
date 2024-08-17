<p align="center">
<img src="https://i.imgur.com/z2VWbpd.png" />
<h2 align="center">The most optimized Staff Core on the market.</h2>
</p>
<br>
<p align="center">
<img src="https://img.shields.io/discord/515954361810157568?color=7289DA&label=Discord&logo=discord&logoColor=7289DA&link=https://discord.gg/6NRjM9N7uk" />
<img src="https://img.shields.io/badge/version-2.1.7-blue">
<img src="https://img.shields.io/badge/API-Docs-blue">
</p>

## API

### Example

<a href="https://github.com/PulsirDevelopment/Lunar/blob/main/Example/src/main/java/net/pulsir/example/Example.java">Click here to view example of API usage.</a>

### Maven

> [!CAUTION]
> API Version does not always match with plugin release version. Github & GitBook Documentation will always hold the newest API version available 

```xml

<repositories>
    <repository>
      <id>pulsir-repository-releases</id>
      <name>Pulsir Repository</name>
      <url>https://repository.pulsir.net/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
      <groupId>net.pulsir</groupId>
      <artifactId>Lunar</artifactId>
      <version>2.1.7</version>
      <scope>provided</score>
    </dependency>
</dependencies>
```

### Gradle

```text
repositories {
    maven {
        name = "pulsirRepositoryReleases"
        url = uri("https://repository.pulsir.net/releases")
    }
}

dependencies {
    compileOnly "net.pulsir:Lunar:2.1.7"
}
```

## License

This plugin is licensed under MIT.

Libraries used in project:
- MongoDB,
- Jedis,
- HikariCP,
- PaperAPI,
- PlaceholderAPI,

## Download

Plugin is available for download on SpigotMC & BuildByBit

- [SpigotMC](https://www.spigotmc.org/resources/lunar-optimized-staff-core-placeholderapi.116639/)
- [BuildByBit](https://builtbybit.com/resources/lunar-optimized-staff-core.44303/)
