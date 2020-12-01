/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.obsidianbreaker;

import lombok.Getter;
import lombok.Setter;

public class BlockStatus {
    @Getter @Setter private float damage = 0.0F, maxDamage;
    @Getter @Setter private boolean modified = true;
    @Getter private final String blockHash, chunkHash;
  
  BlockStatus(String blockHash, String chunkHash, float maxDamage) {
    this.blockHash = blockHash;
    this.chunkHash = chunkHash;
    this.maxDamage = maxDamage;
  }
}
