A scenario defines intervals of time during gameplay, spawning enemies, weapons, and miscellaneous items based on the
specified conditions. Each interval can be of type "oneshot" or "fixed", dictating how entities are spawned. The
scenario can end with three different behaviors: "FINITE", "INFINITE", or "LOOPING".


Scenarios are stored in XML files and may be loaded from the game startup screen. While an invalid scenario should be
detected and stop the game from launching, there may be some rare situations that have unfortunately not been tested.

### Interval Attributes

- `type`: Specifies the type of interval. It can be either "oneshot" or "fixed".
- `time`: Defines the time at which the interval ends (in seconds, from the beginning of the game)

### End Reached Behavior

The scenario script supports three end reached behaviors:

1. **FINITE**: The scenario ends when it reaches its final time interval and the game ends.
2. **INFINITE**: The scenario stays at the last section indefinitel.
3. **LOOPING**: The scenario restarts from the beginning after reaching its end.

### Interval Type: "oneshot" vs "fixed"

- **"oneshot"**: In an interval of type "oneshot", each enemy, weapon, and miscellaneous entity listed within the
  interval is spawned once when the interval is reached. This means that every entity defined within the "oneshot"
  interval will be instantiated once and only once during gameplay, regardless of the interval duration. Multiple
  instances may be spawned by repeating an entry the number of time needed. This is useful for scenarios where you want
  specific items or enemies to appear at precise moments in the game, without repetition.

- **"fixed"**: Conversely, an interval of type "fixed" utilizes predefined frequencies for spawning enemies, weapons, 
  and miscellaneous items as specified in the scenario script. The frequency of spawning for each entity is defined
  with the "frequency" attribute. Useful for scenarios where it is desired that certain ennemies and weapons appear at 
  regular intervals throughout gameplay.

## Available entities
### Weapons:
- Pistol
- Knife
- RocketLauncher
- ShotGun
- RubberWeapon
- TrapPlacer

### Enemies:
- WalkingEnemy
- SmartEnemy
- ExplodingEnemy

### Miscellaneous:
- Bandages

## Example

Here's an example scenario script:

```xml
<scenario endReachedBehaviour="LOOPING">
    <interval type="oneshot" time="3"> <!-- Interval 1, starts at 0, ends at 3, spawns one pistol, one knife, and one bandage -->
        <weapons>
            <weapon name="Pistol"/>
            <weapon name="Knife"/>
        </weapons>
        <enemies>
            <!-- No enemies in this interval -->
        </enemies>
        <miscs>
            <misc name="Bandages"/>
        </miscs>
    </interval>
    <interval type="fixed" time="10"> <!-- Interval 2, starts at 3, ends at 10, spawns WalkingEnemy at a rate of 0.3 per second, and SmartEnemy at 0.1 per second -->
        <weapons>
            <!-- No weapons in this interval -->
        </weapons>
        <enemies>
            <enemy rate="0.3" name="WalkingEnemy"/>
            <enemy rate="0.1" name="SmartEnemy"/>
        </enemies>
        <miscs>
            <!-- No miscellaneous items in this interval -->
        </miscs>
    </interval>
    <interval type="oneshot" time="20"> <!-- Interval 3, starts at 10, ends at 20, spawns one shotgun and one bandage -->
        <weapons>
            <weapon name="ShotGun"/>
        </weapons>
        <enemies>
            <!-- No enemies in this interval -->
        </enemies>
        <miscs>
            <misc name="Bandages"/>
        </miscs>
    </interval>
</scenario>
```