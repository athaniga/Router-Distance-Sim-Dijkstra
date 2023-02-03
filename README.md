This program reads routing tables that come in the form of a .txt file.

The format of the text file must follow:

**The number of routers must be on the first line**

**Numbered router and its associated link**

*For example:*

    6

    R1: (R2, 20), (R3, 40), (R4, 100)  

    R2: (R1, 20), (R4, 60)

    R3: (R1, 40), (R6, 50)

    R4: (R2, 60), (R5, 85), (R1, 100)

    R5: (R4, 85)

    R6: (R3, 50)

In this example router 1 has an arbitrary distance of "20" to router 2, a distance of "40" to router 3, and distance of 
"100" to router 4.

Upon initial launch of the program, the user must select an appropriate txt file to use