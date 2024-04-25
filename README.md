<img src="img/Logo.svg" align="right" alt="Logo" title="Logo" width="72" height="72" />

# VolleyBall

## About

VolleyBall is a minigame that emulates volleyball for a Spigot Minecraft server. Players can serve a ball on a
volleyball court and punch it to volley it. Volleys are counted when the ball goes over the net. Multiple courts can be
created and modified with this plugin.

## Purpose

VolleyBall was originally written for a tropical island themed event on a Minecraft server. This was to add more player
interactive content to the build to make it feel more lively. From there, this plugin has grown more complex to allow
other servers to enjoy the features of VolleyBall for themselves.

## Usage

- `/volleyball help` - Show plugin help
- `/volleyball reload` - Reload configs and delete ball entities
- `/volleyball list` - List all courts
- `/volleyball create <name>` - Create a new court
- `/volleyball info <name>` - Get info about a court
- `/volleyball remove <name>` - Delete a court
- `/volleyball animations <name> <true/false>` - Enable or disable ball animations
- `/volleyball bounds <name> <pos1/pos2/set>` - Set the court bounds based on player location
- `/volleyball editable <name> <true/false>` - Enable or disable editing of court blocks
- `/volleyball enabled <name> <true/false>` - Enable or disable a court
- `/volleyball hints <name> <true/false>` - Enable or disable hints on court
- `/volleyball hitradius <name> <number>` - Set the ball hit radius
- `/volleyball name <name> <newname>` - Change a court's name
- `/volleyball net <name> <pos1/pos2/set>` - Set the net bounds based on player location
- `/volleyball particles <name> <true/false>` - Enable or disable ball particles
- `/volleyball restrictions <name> <true/false>` - Enable or disable ball court restrictions
- `/volleyball scoring <name> <true/false>` - Enable or disable scoring
- `/volleyball sounds <name> <true/false>` - Enable or disable court sound effects
- `/volleyball speed <name> <number>` - Set the ball speed
- `/volleyball teams <name> <true/false>` - Enable or disable team scoring
- `/volleyball texture <name> <url>` - Set the ball texture based on a Minecraft skin texture url

### Playing

- Press and release your sneak key to serve the ball when on a court
- Punch when the ball is close to you
- Try to keep the ball from hitting the ground and get it over the net

## Requirements

- Spigot or Paper 1.13 - 1.19 (1.20+ works, but emits a lot of warnings)
- Java 8 or higher

## Installation

To install the plugin, download the latest release, put it in your server plugins folder, and start or restart your
server. This will generate the necessary files for configuration of the plugin, located in `plugins/VolleyBall`.

## Configuration

There is no main configuration, all configuring is done in-game.

- Use the `volleyball create` command to create a court. The court will be created, but not enabled.
- Use the `volleyball bounds <name> <pos1/pos2>` command to select a cuboid region to set as the bounds. Include the
  floor and the air above it, not just the floor!
- Run `volleyball bounds <name> set` to set the selected region as the court bounds.
- Do the same with `volleyball net <name> <pos1/pos2/set>` to set a region as the net. Select from the floor to the top
  of the net!
- Use the other commands to customize your court to your liking.
- Once you are finished, run `volleyball enabled <name> true` to enable your court. This court can not have its blocks
  modified while it is enabled, so make sure you make build edits before enabling!

Do not modify the courts config, as you can possibly break your saved courts!

For the message configuration, you can use color codes. You can also use the placeholders used per message, as shown in
the default configuration. The messages and their names should explain what they are used for.

### Permissions

- `volleyball.help` - Allow using the help command
- `volleyball.reload` - Allow reloading the plugin
- `volleyball.create` - Allow creating a court
- `volleyball.info` - Allow seeing info about courts
- `volleyball.list` - Allow seeing the list of courts
- `volleyball.remove` - Allow removing a court
- `volleyball.animations` - Allow enabling ball animations
- `volleyball.bounds` - Allow setting court bounds
- `volleyball.editable` - Allow enabling court editing
- `volleyball.enabled` - Allow enabling court
- `volleyball.hints` - Allow enabling court hints
- `volleyball.hitradius` - Allow setting ball hit radius
- `volleyball.name` - Allow setting court name
- `volleyball.net` - Allow setting court net
- `volleyball.particles` - Allow enabling ball particles
- `volleyball.restrictions` - Allow enabling ball restrictions
- `volleyball.scoring` - Allow enabling scoring
- `volleyball.sounds` - Allow enabling court sounds
- `volleyball.speed` - Allow setting ball speed
- `volleyball.teams` - Allow enabling team scoring
- `volleyball.texture` - Allow setting ball texture

## Demonstration

<div align="center" ><img src="img/demo.gif" alt="Demonstration" title="Demonstration" /></div>

## Building
1. Clone or download this repository.
2. Run `./gradlew shadowJar` in the directory of the project.
3. `/build/libs/VolleyBall.jar` should have been generated.

You can also grab `VolleyBall.jar` from the latest releases.

## Troubleshooting

Ball not spawning?

- If you are using world guard, enable armor stand spawning in the region the court is in.
- Also make sure block-plugin-spawning is set to false in the world guard config.
- If you are using EssentialsProtect, make sure to not block armor stand spawning.
