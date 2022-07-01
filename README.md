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
* [final UML](https://github.com/carloronconi/ing-sw-2022-arcuri-prosio-ronconi/tree/main/final-UML)

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

#### Total model coverage:

| Total  | Percentage |
|--------|------------|
| Class  | 100 % |
| Method | 97 %       |
| Line   | 92 % |

#### Model coverage by classes:

| Character cards    | Percentage Methods | Percentage Lines |
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
| Messenger          | 100 %              | 100 %            |
| Monk               | 100 %              | 100 %            |
| MushroomMerchant   | 100 %              | 100 %            |
| Musician           | 100 %              | 100 %            |
| Princess           | 100 %              | 100 %            |
| SwapperCharacter   | 100 %              | 100 %            |
| Usurer             | 100 %              | 100 %            |
| Witch              | 100 %              | 100 %            |

| Student managers and others | Percentage Methods | Percentage Lines |
|-----------------------------|--------------------|------------------|
| Bag                         | 100 %              | 100 %            |
| CharacterStudentCounter     | 100 %              | 100 %            |
| Cloud                       | 100 %              | 100 %            |
| ConcreteStudentCounter      | 100 %              | 100 %            |
| DiningRoom                  | 100 %              | 100 %            |
| Entrance                    | 100 %              | 100 %            |
| IslandManager               | 100 %              | 94 %             |
| IslandTile                  | 100 %              | 86 %             |
| StudentCounter              | 100 %              | 100 %            |
| ConverterUtility            | 100 %              | 83 %             |
| GameModel                   | 95 %               | 88 %             |
| PawnColor                   | 100 %              | 100 %            |
| Player                      | 100 %              | 100 %            |
| ProfessorManager            | 100 %              | 100 %            |
| TowerColor                  | 100 %              | 100 %            |

**NB**: The percentage of coverage of the tests could encounter a slight variation due to a failure to execute
lines of code relating to the effect of the character cards, which are different in each game model initialization.

The tests carried out concern the classes of the model.

For most of them, test classes were created in which all the methods within them were treated.
For some "simple" classes (eg Bag, PawnColor etc) no test classes have been created as they have been used inside others.
The same reasoning applies to some methods.

## Usage

Follow the steps below to play correctly

1. Clone this repository
2. Open the terminal

### Server

3. Open the properties of the [server jar file](https://github.com/carloronconi/ing-sw-2022-arcuri-prosio-ronconi/blob/main/deliveries/jar/artifacts/Server/PSP7.jar)
4. Copy the absolute path of the file
5. Enter the following command without parentheses
```bash
java -jar [absolute path]
```

### CLI

3. Open the properties of the [CLI jar file](https://github.com/carloronconi/ing-sw-2022-arcuri-prosio-ronconi/blob/main/deliveries/jar/artifacts/ClientCli/PSP7.jar)
4. Copy the absolute path of the file
5. Enter the following command without parentheses
```bash
java -jar [absolute path]
```

### GUI

#### MacOs/linux

3. Open the properties of the [GUI jar file for MacOS/linux](https://github.com/carloronconi/ing-sw-2022-arcuri-prosio-ronconi/blob/main/deliveries/jar/artifacts/ClientGui/PSP7.jar)
4. Copy the absolute path of the file
5. Enter the following command without parentheses
```bash
java -jar [absolute path]
```

#### Windows

3. Open the properties of the [GUI jar file for Windows](https://github.com/carloronconi/ing-sw-2022-arcuri-prosio-ronconi/blob/main/deliveries/jar/artifacts/ClientGuiWin/PSP7.jar)
4. Copy the absolute path of the file
5. Enter the following command without parentheses
```bash
java -jar [absolute path]
```

## Software Used

**Astah**: initial UML diagrams

**Astah**: Sequence diagram

**JavaFX**: GUI

**IntelliJ**: final UML diagrams








