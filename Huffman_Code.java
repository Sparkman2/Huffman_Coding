import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

class Node implements Comparable<Node>{

    int num;
    char chara;
    Node l;
    Node r;


    public Node(int num, char chara) {
        this.chara = chara;
        this.num = num;
        l = r = null;
    }

    public Node(int num) {
        this.num = num;
        l = null;
        r = null;
    }
    public int getIntegerValue() {
        return num;
    }

    public char getCharValue() {
        return chara;
    }

    //No usage but needed for Comparable interface
    @Override
    public int compareTo(Node o) {
        return this.num - o.num;
    }

}
public class HuffmanSubmit implements Huffman {

    HashMap<Character, Integer> freq_map = new HashMap<>();;
    HashMap <Character, String> ch_map = new HashMap<>();;
    HashMap<String,Character> copy_map = new HashMap<>();
    int counter = 0;

    public HuffmanSubmit() {
    }


    public void encode(String inputFile, String outputFile, String freqFile){
        BinaryIn input;
        BinaryOut output;

        input = new BinaryIn(inputFile);
        output = new BinaryOut(outputFile);
        for (int i = 0; input.isEmpty() == false; i++) {
            char c = input.readChar();
            freq_map.put(c, 0);
        }

        input=new BinaryIn(inputFile);
        for (int i = 0; input.isEmpty() == false; i++) {
            char c = input.readChar();
            counter+=1;
            //putting chars and their frequencies to the frequency hashmap
            freq_map.put(c, freq_map.get(c) + 1);

        }

        //writing the frequency file
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter (new FileWriter(freqFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //gets the binary and frequenecy that is going to be on output
        //Keeps printing out on a new line
        for (char key:freq_map.keySet()) {
            try {
                StringBuilder string = new StringBuilder(Integer.toBinaryString(key));
                while (string.length() < 8) {
                    string.insert(0, "0");
                }
                writer.write(string + ":" + freq_map.get(key));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Close the writer
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tree(PQ(),"");

        input = new BinaryIn(inputFile);
        output.write(counter);

        //Gets the binary code and then does another loop that converts it to boolean
        //so it can be decoded
        for (int i = 0; input.isEmpty() == false; i++) {
            char c = input.readChar();
            String binary = ch_map.get(c);
            char[] code = binary.toCharArray();
            for(int j = 0; j < code.length; j++){
                char ch = code[j];
                if (ch == '0') {
                    output.write(false);
                }
                if (ch == '1') {
                    output.write(true);
                }
            }
        }
        output.flush();
    }
    
    public void decode(String inputFile, String outputFile, String freqFile){

        BinaryIn input;
        BinaryOut output = new BinaryOut(outputFile);

        Tree(PQ(),"");

        input = new BinaryIn(inputFile);
        String string = "";
        boolean value;
        int size = input.readInt();

        //Decoding by checking true and false from the encode method
        while(size > 1) {
            while (this.copy_map.containsKey(string) == false) {

                value = input.readBoolean();
                if(value) {
                    string = string + "1";
                }if(!value) {
                    string = string + "0";
                }
            }

            output.write(copy_map.get(string));
            string = "";
            size -= 1;
        }

        output.flush();
    }


    //Adds Huffman code to each the character and copy character map
    public void Tree(Node root, String string) {
        if (root.l == null && root.r == null) {
            char v1 = root.getCharValue();
            ch_map.put(v1, string);
            copy_map.put(string, v1);
            return;
        }
        Tree(root.r, string + "1");
        Tree(root.l, string + "0");
    }

    //Priority queue method and performs Huffman algorithm
    public Node PQ() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (char key:freq_map.keySet()) {
            Node node = new Node(freq_map.get(key),key);
            queue.add(node);
        }
        while (queue.size() > 1) {
            Node node1 = queue.poll();
            Node node2 = queue.poll();
            int v2 = node2.getIntegerValue();
            int v1 = node1.getIntegerValue();

            Node newNode = new Node(v1+v2);
            newNode.l = node1;
            newNode.r = node2;
            queue.add(newNode);
        }
        //returns root node
        return queue.poll();
    }

    public static void main(String[] args) throws IOException {
        Huffman  huffman = new HuffmanSubmit();

        huffman.encode("ur.jpg", "ur.enc", "freq.txt");

        huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");

    }

}