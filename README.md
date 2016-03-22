# SimplePromote [![Build Status](https://ci.wertarbyte.com/job/SimplePromote/badge/icon)](https://ci.wertarbyte.com/job/SimplePromote)
A very simple promote plugin for Bukkit.

## Installation
Just [download the jar][ci] and put it in your plugin directory. Once you restart your server, a default
config will be created.
You need to have [Vault][vault] installed and your economy plugin and your permissions plugin need to
support [Vault][vault].

[ci]: https://ci.wertarbyte.com/job/SimplePromote/lastStableBuild/
[vault]: http://dev.bukkit.org/bukkit-plugins/vault/

The default config contains two ranks that players can promote to.

```yaml
news:
ranks:
  - name: Donator # name of the promote rank (for display only)
    cost: 100 # cost
    group: promotion.donator # group the player will be assigned to
  - name: Rich person
    cost: 1000
    group: promotion.rich
```
Note that ranks can only be upgraded in
the same order that they are specified in the configuration. I.e. you can only become a _Rich person_ if
you already are a _Donator_ and pay another 1000 bucks.

## Commands and permissions

| Command     | Permission (default)             | Description                                                                 |
|-------------|----------------------------------|-------------------------------------|
| `/promote`  | `simplepromote.promote` (`true`) | Promote to the next available rank. |
| `/uprank`   | `simplepromote.promote` (`true`) | _alias for `/promote`_              |

## License
SimplePromote is licensed under the MIT license, see the [license file][license].

[license]: https://github.com/leMaik/NewsPlus/blob/master/LICENSE