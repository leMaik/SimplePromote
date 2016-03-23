# SimplePromote [![Build Status](https://ci.wertarbyte.com/job/SimplePromote/badge/icon)](https://ci.wertarbyte.com/job/SimplePromote)
A very simple promote plugin for Bukkit.

## Installation
Just [download the jar][ci] and put it in your plugin directory. Once you restart your server, a default
config will be created.
You need to have [Vault][vault] installed and your economy plugin and your permissions plugin need to
support [Vault][vault].

[ci]: https://ci.wertarbyte.com/job/SimplePromote/lastStableBuild/
[vault]: http://dev.bukkit.org/bukkit-plugins/vault/

The default config contains two ranks that players can be promoted to.

```yaml
news:
ranks:
  - name: Donator # name of the promote rank (for display only)
    cost: 100 # cost
    group: promotion.donator # group the player will be assigned to
  - name: Rich person
    cost: 1000
    group: promotion.rich
    requires: promotion.donator
broadcastPromotedPlayers: true # whether to broadcast when a player was promoted
```
Note that every group specified with `requires` may only have one next rank to promote to. If multiple possibilities
are configured, the last entry is used.

## Commands and permissions

| Command     | Permission (default)             | Description                                                                 |
|-------------|----------------------------------|-------------------------------------|
| `/promote`  | `simplepromote.promote` (`true`) | Promote to the next available rank. |
| `/uprank`   | `simplepromote.promote` (`true`) | _alias for `/promote`_              |
| `/rankup`   | `simplepromote.promote` (`true`) | _another alias for `/promote`_      |

## License
SimplePromote is licensed under the MIT license, see the [license file][license].

[license]: https://github.com/leMaik/NewsPlus/blob/master/LICENSE