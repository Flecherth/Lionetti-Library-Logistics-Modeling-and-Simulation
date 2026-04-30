# Lionetti-Library-Logistics-Modeling-and-Simulation
My simulation, the "Lionetti Library Logistics" will attempt to simulate the environment of a public library answering the question "What is the most efficient way to sort and shelve books?". The simulation will be simulating using only one age group of books to be selected and only those books will be sorted (otherwise, a YA RIO could be across the entire library in comparison to a J RIO). The shelves will also be a uniform distance from each other. The simulation will not include variables such as book weight, size, or a librarian's knowledge of the layout of the library or the books in its collection. 

Project Status
Every method fully implemented and test runs completed

Installation Instructions:
In your IDE terminal type git clone (https://github.com/Flecherth/Lionetti-Library-Logistics-Modeling-and-Simulation/tree/master) or download files any other way
Choose a JDK
Set src as source root
Run driver

Usage:
Initiate the driver and input the name of your file as well as the purpose of this run. Then, input values for shelf size, number of shelves, number of carts, and cart size keeping in mind that there must be at least 1 shelf and that a library cannot be overfilled so number of carts and cart size cannot exceed half of the number of shelves times shelf size. The program might take a couple of seconds to run if large numbers were chosen as ACO takes an exponentially long time to run. Expected results should be that FIFS sorting should have faster times than every other library, less total distance traveled, and higher shelf usage, ACO should have the second lowest distance traveled with high shelving times, mergeSort should outperform insertionSort on average, and LibrarySort should have average shelving times with low shelf usage.

Architecture Overview:
The key entity types in this simulation are Libraries, Individual Books, Cart of Books, and Shelves
• Libraries will contain books, carts of books, and shelves.
• Carts of books will contain a wide variety of books that will need to be sorted
• Books will have a title and author
• Shelves will contain a wide variety of mostly sorted books along with a range for which books
should be placed on it (these attributes work differently in the first-in first-shelved method)
A majority of the architectural changes were method additions to classes like Library including fillShelves, shiftShelves, etc... I renamed BinarySort to BinaryInsertionSort to make it more accurate, did not include currentLocation and Destination variables for the book class as they were unnecessary, and had Library utilize the Metrics class instead of the Driver.
