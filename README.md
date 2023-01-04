# Huffman_Coding

This project can compress a txt file or a jpeg by encoding the file and then decoding it.

In the encoding method, I took the input file and attached them to a frequency map where I could get all the frequencies of each character. I then call the priority queue method which does the Huffman coding algorithm and then write the codes onto a binary tree. I then convert the binary codes into booleans so that it can be decoded. 

In the decoding method, I reassigned Huffman codes again and put them into a map. Then decoded by checking the value with my copy_map which flips the key and value and I got the decoded character from the map. Additionally, I created a Node class so I would be able to create the Priority Queue and Tree methods.
