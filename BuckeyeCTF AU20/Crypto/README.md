# Postmodern Petri Dish

## Question
> The boys back in the lab were studying some bacteria and stuff when they found an repeating RNA sequence they'd never seen before. Something looked off about it, but after a few minutes of thinking they got bored and decided to try and give mice gills or something. Could you be ?the only one left with the attention span to figure it out?  

> ccauuagaggcuucagaagaggccaccuagagaguugagggggggaucgaaucc  
flag format is: osuctf{ANSWER}

## Solution (by b01lers)
All we really need to do is go to: [https://en.wikipedia.org/wiki/Genetic_code#RNA_codon_table](https://en.wikipedia.org/wiki/Genetic_code#RNA_codon_table)

And use the table to do:  
cca -> P Proline  
uua -> L Leucine  
gag -> E Glutamic Acid  
gcu -> A Alanine  
uca -> S Serine  
gaa -> E Glutamic Acid  
gag -> E Glutamic Acid  
gcc -> A Alanine  
acc -> T Threonine  
uag -> _ STOP (Amber)  
aca -> T Threonine  
guu -> V Valine  
gag -> E Glutamic Acid  
ggg -> G Glycine  
ggg -> G Glycine  
auc -> I Isoleucine  
gaa -> E Glutamic Acid    
ucc -> S Serine  
This spells out PLEASEEAT_TVEGGIES because the author fucked up, but that's okay, we can guess that it's actually PLEASEEATURVEGGIES.
