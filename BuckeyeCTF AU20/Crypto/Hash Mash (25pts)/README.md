# Postmodern Petri Dish

## Question

> osuctf{3e2e95f5ad970eadfa7e17eaf73da97024aa5359_2346ad27d7568ba9896f1b7da6b5991251debdf2_b47f363e2b430c0647f14deea3eced9b0ef300ce_fc19318dd13128ce14344d066510a982269c241b_8fcd25a39d2037183044a8897e9a5333d727fded_b295d117135a9763da282e7dae73a5ca7d3e5b11}

## Solution (by b01lers)

Using crackstation: [https://crackstation.net/](https://crackstation.net/)

By splitting up the codes by "\_",  
3e2e95f5ad970eadfa7e17eaf73da97024aa5359  
2346ad27d7568ba9896f1b7da6b5991251debdf2  
b47f363e2b430c0647f14deea3eced9b0ef300ce  
fc19318dd13128ce14344d066510a982269c241b  
8fcd25a39d2037183044a8897e9a5333d727fded  
b295d117135a9763da282e7dae73a5ca7d3e5b11

We get:
3e2e95f5ad970eadfa7e17eaf73da97024aa5359 sha1 potato  
2346ad27d7568ba9896f1b7da6b5991251debdf2 sha1 hash  
b47f363e2b430c0647f14deea3eced9b0ef300ce sha1 is  
fc19318dd13128ce14344d066510a982269c241b sha1 good  
8fcd25a39d2037183044a8897e9a5333d727fded sha1 with  
b295d117135a9763da282e7dae73a5ca7d3e5b11 sha1 salt

Flag: osuctf{potato_hash_is_good_with_salt}

Alternatively (My Solution):  
Analyze Hash Type: [https://www.tunnelsup.com/hash-analyzer/](https://www.tunnelsup.com/hash-analyzer/)  
Crack Hash: [http://reverse-hash-lookup.online-domain-tools.com/](http://reverse-hash-lookup.online-domain-tools.com/)