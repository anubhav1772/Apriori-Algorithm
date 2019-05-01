## Introduction
Association rule mining is the powerful tool now a days in Data mining. It identifies the correlation between the items in large databases. A typical example of Association rule      mining  is Market Basket analysis. In this method or approach it examines the buying habits of the customers by identifying the associations among the items purchased by the customers in their baskets.
## Algorithm
Apriori algorithm is one of the Data Mining algorithm which is used to find the frequent items/ itemsets from a given data repository. The algorithm mainly involves two steps: 
* Pruning, and 
* joining. 
##### Apriori Property: 
If an item X is joined with item Y, then  
###### ```Support(X U Y) = min (Support(X), Support(Y))```,
where Support(X) = no. of transactions which contain the itemset X / total no. of transaction 

Apriori uses a "bottom up" approach, where frequent subsets are extended one item at a time (a step known as candidate generation), and groups of candidates are tested against the data. The algorithm terminates when no further successful extensions are found.  
Apriori uses breadth-first search and a tree structure to count candidate item sets efficiently. It generates candidate item sets of length k from item sets of length k − 1. Then it prunes the candidates which have an infrequent sub pattern. According to the downward closure lemma, the candidate set contains all frequent k-length item sets. After that, it scans the transaction database to determine frequent item sets among the candidates. 
