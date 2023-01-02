# 🎉 | AnnounceMessages
AnnounceMessages is a plugin that allows you customize the messages when the players join to server or leave it, can add colors, placeholders, actions and create your own formats!

[![CodeFactor](https://www.codefactor.io/repository/github/initsync/announcemessages/badge)](https://www.codefactor.io/repository/github/initsync/announcemessages)

# 🛠️ | API Installation
AnnounceMessagesAPI installation is very easy, you can import the plugin API using a dependency manager such as Maven or Gradle. Or just add the jar to BuildPath of your project.

To get the jar, either download it from [GitHub](https://github.com/InitSync/XConfig/releases) releases. Or [Build it locally](https://github.com/InitSync/XConfig#--build)

```Gradle
repositories {
  maven("https://jitpack.io/")
  mavenLocal()
}

dependencies {
  compileOnly("com.github.InitSync.AnnounceMessages:api:3.0")
}
```

```Xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io/</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.InitSync.AnnounceMessages</groupId>
    <artifactId>api</artifactId>
    <version>3.0</version>
  </dependency>
</dependencies>
```

# ➕ | Contribute
Do you want contribute with the library?

* [Make a Pull Request](https://github.com/InitSync/AnnounceMessages/compare)
* [Issues](https://github.com/InitSync/AnnounceMessages/issues/new)

# ✅ | Build
If you want build the project locally, download it, you must be had Gradle and Java 8+ for this.

Now for build the project
```
git clone https://github.com/InitSync/AnnounceMessages
cd AnnounceMessages
./gradlew shadowJar
```

The files will be at the `AnnounceMessages/bin` folder.

# 🎫 | License
This project is licensed under the GNU General Public License v3.0 license, for more details see the file [License](LICENSE)
