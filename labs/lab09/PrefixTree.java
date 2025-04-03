import java.util.HashMap;
import java.util.Map;

// Represents a single node in the Trie
class TrieNode {
    private char val;
    private Map<Character, TrieNode> children;
    private boolean isEndOfWord;

    // Constructor to initialize a TrieNode
    public TrieNode(char val) {
        this.val = val;
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }

    // Marks this node as the end of a word
    public void markAsLeaf() {
        this.isEndOfWord = true;
    }

    // Getters
    public char getVal()
    {
        return this.val;
    }

    public Map<Character, TrieNode> getChildren ()
    {
        return this.children;
    }

    public Boolean getIsEndOfWord() {
        return this.isEndOfWord;
    }
}

class PrefixTree 
{
    TrieNode root;

    public PrefixTree()
    {
        this.root = new TrieNode('\0');
    }

    public void insert(String word)
    {
        TrieNode current = root;

        for(char c : word.toCharArray())
        {
            current.getChildren().putIfAbsent(c, new TrieNode(c));
            current = current.getChildren().get(c);
        }

        current.markAsLeaf();
    }

    public Boolean search (String word)
    {
        TrieNode current = this.root;
       
        for(char c : word.toCharArray())
        {
            if(!current.getChildren().containsKey(c) && !current.getIsEndOfWord())
            {
                return false;
            }

            current = current.getChildren().get(c);
        }

        return true;
    }

    public Boolean startsWith (String prefix)
    {
        TrieNode current = this.root;
       
        for(char c : prefix.toCharArray())
        {
            if(!current.getChildren().containsKey(c))
            {
                return false;
            }

            current = current.getChildren().get(c);
        }

        return true;
    }

    private void traverseInternal (TrieNode node, String prefix)
    {
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            if(entry.getValue().getIsEndOfWord())
            {
                System.out.println(prefix + "└── " + entry.getKey() + " (end)");                
            }
            else
            {
                System.out.println(prefix + "└── " + entry.getKey());
            }
            traverseInternal(entry.getValue(), prefix + "  ");
        }
        
    }

    public void traverse ()
    {
        traverseInternal(this.root, "  ");
    }

    public static void main(String[] args) {
        PrefixTree trie = new PrefixTree();
  
        trie.insert("cat");
        trie.insert("car");
        trie.insert("dog");
  
        System.out.println("Search 'cat': " + trie.search("cat")); // true
        System.out.println("Search 'car': " + trie.search("car")); // true
        System.out.println("Search 'bat': " + trie.search("bat")); // false
        System.out.println("StartsWith 'ca': " + trie.startsWith("ca")); // true
        System.out.println("StartsWith 'do': " + trie.startsWith("do")); // true
        System.out.println("StartsWith 'bo': " + trie.startsWith("bo")); // false
  
        System.out.println("Trie Structure:");
        trie.traverse();
    }
}

