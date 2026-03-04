# Lionetti-Library-Logistics-Modeling-and-Simulation
My simulation, the "Lionetti Library Logistics" will attempt to simulate the environment of a public library answering the question "What is the most efficient way to sort and shelve books?". The simulation will be simulating using only one age group of books to be selected and only those books will be sorted (otherwise, a YA RIO could be across the entire library in comparison to a J RIO). The shelves will also be a uniform distance from each other. The simulation will not include variables such as book weight, size, or a librarian's knowledge of the layout of the library or the books in its collection. 

Project Status
Classes Fully Implemented: BinaryInsertionSort, Book, Cart, FIFS, Library, SequentialShelving
Classes Partially Implemented: Driver, Metrics, LibrarySort
Classes to be Implemented: ACO
Many changes are coming to the overall structure of the simulation including the ability to get the results of different algorithms on the same library as well as fully implementing the LibrarySort and ACO algorithms. There were multiple changes to the structure but the most important changes were my decision to not use LibrarySort to sort carts and instead utilize insertion sort and merge sort to sort carts and LibrarySort to place books on to shelves.

Installation Instructions:
In your IDE terminal type git clone (https://github.com/Flecherth/Lionetti-Library-Logistics-Modeling-and-Simulation/tree/master) or download files any other way
Choose a JDK
Set src as source root
Run driver

Usage:
Initiate the driver and generate carts by inputting 1. From there you can either sort the cart using insertion sort (option 3) and shelve utilizing sequential shelving (option 5) or you can shelve without sorting using FIFS (option 4). Once the books from the cart are shelved you can calculate the shelf usage using option 8, print the results with option 9, and end the program with option 10. FIFS sorting should have lower distance traveled in comparison to SequentialShelving but greater %usage as unutilized shelves are not apart of the overall shelf utilization calculations.

Architecture Overview:
The key entity types in this simulation are Libraries, Individual Books, Cart of Books, and Shelves
• Libraries will contain books, carts of books, and shelves.
• Carts of books will contain a wide variety of books that will need to be sorted
• Books will have a title and author
• Shelves will contain a wide variety of mostly sorted books along with a range for which books
should be placed on it (these attributes work differently in the first-in first-shelved method)
A majority of the architectural changes were method additions to classes like Library including fillShelves, shiftShelves, etc... I renamed BinarySort to BinaryInsertionSort to make it more accurate, did not include currentLocation and Destination variables for the book class as they were unnecessary, and had Library utilize the Metrics class instead of the Driver.
