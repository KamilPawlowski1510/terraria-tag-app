# terraria-tag-app
A terraria companion app to help you progress through the game.

## Introduction

The ***Terraria Tag App*** is a kotlin console app designed to help players progress through the game. As the user progresses through terraria they can update which bosses they have defeated in the app and will be shown which weapons are available to them as well as the DPS of said weapons. This is to allow players to get the best weapon available to them without needing copious hours of research of dumb luck.

![Untitled1](https://github.com/KamilPawlowski1510/terraria-tag-app/assets/145615652/f8f00cc7-30ef-45a9-ae31-5c680fd5983f)

![Untitled2](https://github.com/KamilPawlowski1510/terraria-tag-app/assets/145615652/e69f0dc5-ed83-4bf9-8ceb-253c673c300b)

![Untitled](https://github.com/KamilPawlowski1510/terraria-tag-app/assets/145615652/af3289c7-5956-46ec-ad3a-fa3fa601acb3)


## Features

The Terraria Tag App is still in development and may not have all of the features the finished product will have. Currently the app has:
- A main Menu:
  - Here the user can see:
    - the next boss they need to defeat
    - how many bosses they've defeated
    - the best weapon to get at current progression
    - how many weapons are available to them
  - You have an option to reset progress back to a default state
  - The option to go into a boss menu and a weapons menu
- Boss Menu:
  - Here the user can toggle which bosses they have defeated
  - The menu automatically highlights the next one for them to defeat blue, defeated ones green and undefeated ones red
- Weapon Menu
  - Here the user can see which weapons are available to them
  - You can sort the weapons by highest or lowest dps
  - You can choose to see all weapons, all available weapons or all unavailable weapons
  - You can search by with a tag to see al the weapons that have it. Tags include: the type of weapon, the subtype of the weapon, the biome it's found in, the height it's found in and event it's gotten in
- Persistence
  - The app also has persistence saving the user data into two XML files, one for bosses and one for weapons 

## Scope

Currently the app is in an early stage so certain content will be missing. These limitations include:
- Currently the user cannot add any new weapons or bosses
- There is only a limited amount of weapons and bosses added
- The DPS formula only accounts for single target damage and doesn't account for projectiles or additional effects

## Getting Started with the Project

1. Download the project
2. Open it in an IDE (such as IntelliJ)
3. Select run (or press Shift + F10)
4. Type in the option into the console which you wish to see

## Project Authors / Contributors

- **Kamil Pawlowski**
- **Siobhan Drohan**
