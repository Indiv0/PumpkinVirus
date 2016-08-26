# [INACTIVE] PumpkinVirus [![Travis CI](https://secure.travis-ci.org/Indiv0/pumpkin-virus.png)](http://travis-ci.org/#!/Indiv0/pumpkin-virus)

PumpkinVirus is a plugin for Bukkit which gives players and/or administrators the ability to create a pumpkin "virus" which spreads across the map.
It is meant to provide a roleplay aspect to a server, and is best suited for apocalyptic servers.

## Administrators

### Download

You can find the latest builds of the plugin at my [ci](http://ci.nikitapek.in/job/pumpkin-virus/).
You can find stable downloads with detailed changelog information in the [files](http://dev.bukkit.org/bukkit-plugins/pumpkin-virus/files/) section of the plugin's BukkitDev page.

### Installation

Simply drop the latest .jar into the /plugins directory of your server.

### Usage

Further plugin information can be found at the plugin's [BukkitDev](http://dev.bukkit.org/bukkit-plugins/pumpkin-virus/) page.

## Developers

PearlNerf does not have a formal API at the moment, but you can download the latest version via maven by adding the following snippets to your plugin's pom.xml.

### Repository

    <repositories>
      <repository>
        <id>indiv0's Repo</id>
        <url>http://repo.nikitapek.in/maven/releases</url>
      </repository>
    </repositories>

### Dependency

    <dependency>
      <groupId>in.nikitapek</groupId>
      <artifactId>pumpkin-virus</artifactId>
      <version>1.16.0</version>
    </dependency>
