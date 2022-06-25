# ing-sw-2022-arcuri-prosio-ronconi

**Teacher**: Pierluigi San Pietro

**Group Number**: 7

**Students**:
* [Arcuri Francesco](https://github.com/FrancescoArcuri)
* [Prosio Giulia](https://github.com/giuliaprosio)
* [Ronconi Carlo](https://github.com/carloronconi)


The project consists of a version of the game board "Eriantys", made by Cranio Creations.

## Documentation

The following links lead to the initial and final structures of the UML diagram and Communication Protocol
* [initial UML](https://github.com/carloronconi/ing-sw-2022-arcuri-prosio-ronconi/blob/main/deliveries/1-initial-UML/UML-model-initial.png)
* [final UML]()

* [Communication Protocol](https://github.com/carloronconi/ing-sw-2022-arcuri-prosio-ronconi/blob/main/deliveries/3-protocol-documentation/protocol-documentation-group7.pdf)

## Functionality

* Basic rules
* Complete rules
* Socket
* CLI
* GUI
* Advanced functionality
    * creation of all 12 characters

## Tests Coverage

#### Model Coverage

| Element | Percentage |
|---------|------------|
| Class | 100 % |
| Method  | 97 %       |
| Line | 92 % |

| CharacterCards     | Percentage Methods | Percentage Lines |
|--------------------|--------------------|------------------|
| AvailableCharacter | 100 %              | 73 %             |
| Centaur            | 100 %              | 100 %            |
| Character          | 100 %              | 100 %            |
| CharacterFactory   | 100 %              | 96 %             |
| CheeseMerchant     | 100 %              | 100 %            |
| ColorSwap          | 100 %              | 100 %            |
| FlagBearer         | 100 %              | 100 %            |
| Juggler            | 100 %              | 100 %            |
| Knight             | 100 %              | 100 %            |
| Messenger          | 50 %               | 50 %             |
| Monk               | 100 %              | 100 %            |
| MushroomMerchant   | 100 %              | 100 %            |
| Musician           | 100 %              | 100 %            |
| Princess           | 100 %              | 100 %            |
| SwapperCharacter   | 100 %              | 100 %            |
| Usurer             | 100 %              | 100 %            |
| Witch              | 100 %              | 100 %            |

| Classes                 | Percentage Methods | Percentage Lines |
|-------------------------|--------------------|------------------|
| Bag                     | 100 %              | 100 %            |
| CharacterStudentCounter | 100 %              | 100 %            |
| Cloud                   | 100 %              | 100 %            |
| ConcreteStudentCounter  | 100 %              | 100 %            |
| DiningRoom              | 100 %              | 100 %            |
| Entrance                | 100 %              | 100 %            |
| IslandManager           | 100 %              | 94 %             |
| IslandTile              | 100 %              | 88 %             |
| StudentCounter          | 100 %              | 100 %            |
| ConverterUtility        | 100 %              | 83 %             |
| GameModel               | 92 %               | 86 %             |
| PawnColor               | 100 %              | 100 %            |
| Player                  | 100 %              | 100 %            |
| ProfessorManager        | 100 %              | 100 %            |
| TowerColor              | 100 %              | 100 %            |

**NB**: The percentage of coverage of the tests could encounter a slight variation due to a failure to execute
lines of code relating to the effect of the character cards, which are different in each game model initialization.

The tests carried out concern the classes of the model.

For most of them, test classes were created in which all the methods within them were treated.
For some "simple" classes (eg Bag, PawnColor etc) no test classes have been created as they have been used inside others.
The same reasoning applies to some methods.

## Usage

Follow the steps below to play correctly

### Windows/MacOs/linux

1. Open the command line

#### Server

2. Open the properties of the server jar file
3. Copy the absolute path of the file
4. Enter the command without parentheses
```bash
java -jar (absolute path)\(file name).jar
```

#### CLI

2. Open the properties of the CLI jar file
3. Copy the absolute path of the file
4. Enter the command without parentheses
```bash
java -jar (absolute path)\(file name).jar
```

#### GUI


## Software Used

**Astah***: UML diagrams

**Astah***: Sequence diagram

**JavaFX**: GUI








