plugins {
	id("net.minecrell.plugin-yml.bukkit") version("0.5.2")
	id("com.github.johnrengelman.shadow") version("7.1.2")
	`java-library`
}

val directory = property("group") as String
val release = property("version") as String
val libraries = "$directory.libraries"

repositories {
	mavenLocal()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
	maven("https://oss.sonatype.org/content/repositories/snapshots/")
	maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
	maven("https://jitpack.io/")
	mavenCentral()
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
	
	compileOnly("me.clip:placeholderapi:2.11.2")
	compileOnly("net.luckperms:api:5.4")
	
	implementation(project(":api"))
	implementation("com.github.InitSync.XConfig:bukkit:1.1.3")
	implementation("com.github.InitSync:XTitle:1.0.1")
}

bukkit {
	name = "AnnounceMessages"
	main = "$directory.plugin.AnnounceMessages"
	authors = listOf("InitSync")

	apiVersion = "1.13"
	version = release
	
	commands {
		register("announcemessages") {
			description = "-> Command to handle the plugin."
			aliases = listOf("amsg")
		}
		
		register("title") {
			description = "-> Command to send titles."
		}
		
		register("actionbar") {
			description = "-> Command to send actionbars."
		}
		
		register("alert") {
			description = "-> Command to send announcements."
			aliases = listOf("broadcast", "announcement")
		}
	}
}

tasks {
	shadowJar {
		archiveFileName.set("AnnounceMessages-$release.jar")
		destinationDirectory.set(file("$rootDir/bin/"))
		
		relocate("net.xconfig.bukkit", "$libraries.xconfig")
		relocate("net.xtitle", "$libraries.xtitle")
	}
	
	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}
	
	clean {
		delete("$rootDir/bin/")
	}
}