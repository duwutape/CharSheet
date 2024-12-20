package org.example.service;

import org.example.classes.*;
import org.example.constants.Constants;
import org.example.model.Choice;
import org.example.model.GameClass;
import org.example.model.Spell;

import java.util.ArrayList;
import java.util.List;

import static org.example.constants.Constants.*;
import static org.example.constants.Spells.*;


public class GameClassService {

    public GameClass createClass(String className, int level) {
        GameClass gameClass;
        switch (className) {
            case BARBARIAN -> gameClass = new Barbarian(-1);
            case BARD -> gameClass = new Bard(-1);
            case CLERIC -> gameClass = new Cleric(-1);
            case DRUID -> gameClass = new Druid(-1);
            case FIGHTER -> gameClass = new Fighter(-1);
            case MONK -> gameClass = new Monk(-1);
            case PALADIN -> gameClass = new Paladin(-1);
            case RANGER -> gameClass = new Ranger(-1);
            case ROUGE -> gameClass = new Rogue(-1);
            case SORCERER -> gameClass = new Sorcerer(-1);
            case WARLOCK -> gameClass = new Warlock(-1);
            case WIZARD -> gameClass = new Wizard(-1);
            default -> gameClass = new GameClass(-1);
        }

        updateClass(gameClass, level);

        return gameClass;
    }

    public void updateClass(GameClass gameClass, int level) {

        while (gameClass.getLevel() < level) {
            gameClass.increaseLevel();

            switch (gameClass.getClass().getName()) {
                case BARBARIAN -> updateBarbarian((Barbarian) gameClass);
                case BARD -> updateBard((Bard) gameClass);
                case CLERIC -> updateCleric((Cleric) gameClass);
                case DRUID -> updateDruid((Druid) gameClass);
                case FIGHTER -> updateFighter((Fighter) gameClass);
                case MONK -> updateMonk((Monk) gameClass);
                case PALADIN -> updatePaladin((Paladin) gameClass);
                case RANGER -> updateRanger((Ranger) gameClass);
                case ROUGE -> updateRouge((Rogue) gameClass);
                case SORCERER -> updateSorcerer((Sorcerer) gameClass);
                case WARLOCK -> updateWarlock((Warlock) gameClass);
                case WIZARD -> updateWizard((Wizard) gameClass);
            }
        }
    }

    private void abilitySkillImprovement(GameClass gameClass) {
        Choice choice = new Choice();
        choice.setName(ABILITY_SCORE_IMPROVEMENT);
        gameClass.addChoose(choice);
    }

    private void expertise(GameClass gameClass, int amount) {
        Choice choice = new Choice();
        choice.setName(EXPERTISE);
        choice.setAmount(amount);
        gameClass.addChoose(choice);
    }

    private void removeKnownSpells(GameClass gameClass, Choice choice, ArrayList<String> posSpells) {

        for (String posSpell : posSpells) {
            boolean found = false;
            for (Spell spell : gameClass.getSpells()) {
                if (spell.getNAME().equals(posSpell)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                choice.addOptions(posSpell);
            }
        }
    }

    private void updateBarbarian(Barbarian barbarian) {
        switch (barbarian.getLevel()) {
            case 0 -> {
                barbarian.addPrimaryAbilities(STRENGTH);
                barbarian.setHitDie(D12);
                barbarian.setHitDieMod(CONSTITUTION);
                barbarian.addSavingThrowProfs(STRENGTH, CONSTITUTION);
                Choice choice = new Choice();
                choice.setName(SKILL_PROFICIENCIES);
                choice.setAmount(2);
                choice.addOptions(ANIMAL_HANDLING, ATHLETICS, INTIMIDATION, NATURE, PERCEPTION, SURVIVAL);
                barbarian.addChoose(choice);
                barbarian.addWeaponProfs(SIMPLE_WEAPON, MARTIAL_WEAPON);
                barbarian.addArmorTraining(LIGHT_ARMOR, MEDIUM_ARMOR, Constants.SHIELD);
            }
            case 1 -> {
                barbarian.setProfBonus(2);
                barbarian.addFeatures(RAGE, UNARMORED_DEFENCE);
                barbarian.setAmountRages(2);
                barbarian.setRageMod(2);
            }
            case 2 -> barbarian.addFeatures(DANGER_SENSE, RECKLESS_ATTACK);
            case 3 -> {
                Choice choice = new Choice();
                choice.setName(BARBARIAN + " " + SUBCLASS);
                choice.setAmount(1);
                choice.addOptions(BERSERKER, TOTEM_WARRIOR);
                barbarian.addChoose(choice);
                barbarian.setAmountRages(3);
            }
            case 4 -> {
                abilitySkillImprovement(barbarian);
            }
            case 5 -> {
                barbarian.setProfBonus(3);
                barbarian.addFeatures(EXTRA_ATTACK, FAST_MOVEMENT);
            }
            case 6 -> {
                switch (barbarian.getSubclass()) {
                    case BERSERKER -> barbarian.addFeatures(MINDLESS_RAGE);
                    case TOTEM_WARRIOR -> {
                        barbarian.addFeatures(ASPECT_OF_THE_BEAST);
                        Choice choice = new Choice();
                        choice.setName(TOTEM_ANIMAL);
                        choice.setAmount(1);
                        choice.addOptions(BEAR, EAGLE, WOLF);
                    }
                }
            }
            case 7 -> {
                barbarian.addFeatures(FERAL_INSTINCT);
            }
            case 8 -> abilitySkillImprovement(barbarian);
            case 9 -> {
                barbarian.setProfBonus(4);
                barbarian.addFeatures(BRUTAL_CRITICAL_1);
                barbarian.setRageMod(3);
            }
            case 10 -> {
                switch (barbarian.getSubclass()) {
                    case BERSERKER -> barbarian.addFeatures(INTIMIDATING_PRESENCE);
                    case TOTEM_WARRIOR -> barbarian.addFeatures(SPIRIT_WALKER);
                }
            }
            case 11 -> barbarian.addFeatures(RELENTLESS_RAGE);
            case 12 -> {
                barbarian.setAmountRages(5);
                abilitySkillImprovement(barbarian);
            }
            case 13 -> {
                barbarian.setProfBonus(5);
                barbarian.addFeatureAtIndex(BRUTAL_CRITICAL_2, barbarian.getFeatures().indexOf(BRUTAL_CRITICAL_1));
                barbarian.removeFeature(BRUTAL_CRITICAL_1);
            }
            case 14 -> {
                switch (barbarian.getSubclass()) {
                    case BERSERKER -> barbarian.addFeatures(RETALIATION);
                    case TOTEM_WARRIOR -> {
                        barbarian.addFeatures(TOTEMIC_ATTUNEMENT);
                        Choice choice = new Choice();
                        choice.setName(TOTEM_ANIMAL);
                        choice.setAmount(1);
                        choice.addOptions(BEAR, EAGLE, WOLF);
                    }
                }
            }
            case 15 -> {
                barbarian.addFeatures(PERSISTENT_RAGE);
                barbarian.setRageMod(4);
            }
            case 16 -> {
                abilitySkillImprovement(barbarian);
                barbarian.setRageMod(4);
            }
            case 17 -> {
                barbarian.setProfBonus(6);
                barbarian.addFeatureAtIndex(BRUTAL_CRITICAL_3, barbarian.getFeatures().indexOf(BRUTAL_CRITICAL_2));
                barbarian.removeFeature(BRUTAL_CRITICAL_2);
                barbarian.setAmountRages(6);
            }
            case 18 -> barbarian.addFeatures(INDOMITABLE_MIGHT);
            case 19 -> abilitySkillImprovement(barbarian);
            case 20 -> barbarian.addFeatures(PRIMAL_CHAMPION);
        }
    }

    private void updateBard(Bard bard) {
        switch (bard.getLevel()) {
            case 0 -> {
                bard.addPrimaryAbilities(CHARISMA);
                bard.setHitDie(D8);
                bard.setHitDieMod(CONSTITUTION);
                bard.addSavingThrowProfs(DEXTERITY, CHARISMA);
                Choice choiceSkill = new Choice();
                choiceSkill.setName(SKILL_PROFICIENCIES);
                choiceSkill.setAmount(3);
                choiceSkill.addOptions(ALL_SKILLS);
                bard.addChoose(choiceSkill);
                Choice choiceTool = new Choice();
                choiceTool.setName(TOOL_PROFICIENCIES);
                choiceTool.setAmount(3);
                choiceTool.addOptions(INSTRUMENTS);
                bard.addChoose(choiceTool);
                bard.addWeaponProfs(SIMPLE_WEAPON);
                bard.addArmorTraining(LIGHT_ARMOR);
            }
            case 1 -> {
                bard.setProfBonus(2);
                bard.addFeatures(SPELLCASTING);
                bard.addFeatures(BARDIC_INSPIRATION_D6);
                bard.addSpellSlot(0, 2);
                bard.addSpellSlot(1, 2);
                bard.setAmountPreparedSpells(4);
            }
            case 2 -> {
                bard.addFeatures(JACK_OF_ALL_TRADES);
                bard.addFeatures(SONG_OF_REST_D6);
                bard.addSpellSlot(1, 3);
                bard.setAmountPreparedSpells(5);
            }
            case 3 -> {
                Choice choice = new Choice();
                choice.setName(BARD + " " + SUBCLASS);
                choice.setAmount(1);
                choice.addOptions(LORE, VALOR);
                bard.addChoose(choice);
                expertise(bard, 2);
                bard.addSpellSlot(1, 4);
                bard.addSpellSlot(2, 2);
                bard.setAmountPreparedSpells(6);
            }
            case 4 -> {
                abilitySkillImprovement(bard);
                bard.addSpellSlot(0, 3);
                bard.addSpellSlot(2, 3);
                bard.setAmountPreparedSpells(7);
            }
            case 5 -> {
                bard.setProfBonus(3);
                bard.addFeatureAtIndex(BARDIC_INSPIRATION_D8, bard.getFeatures().indexOf(BARDIC_INSPIRATION_D6));
                bard.removeFeature(BARDIC_INSPIRATION_D6);
                bard.addFeatures(FONT_OF_INSPIRATION);
                bard.addSpellSlot(3, 2);
                bard.setAmountPreparedSpells(8);
            }
            case 6 -> {
                bard.addFeatures(COUNTERCHARM);
                bard.addSpellSlot(3, 3);
                bard.setAmountPreparedSpells(9);
                switch (bard.getSubclass()) {
                    case LORE -> {
                        Choice choice = new Choice();
                        choice.setName(ADDITIONAL_MAGICAL_SECRETS);
                        choice.setAmount(2);
                        ArrayList<String> posSpells = new ArrayList<>();
                        posSpells.addAll(List.of(ALL_CANTRIPS));
                        posSpells.addAll(List.of(ALL_SPELLS_LVL_1));
                        posSpells.addAll(List.of(ALL_SPELLS_LVL_2));
                        posSpells.addAll(List.of(ALL_SPELLS_LVL_3));

                        removeKnownSpells(bard,choice,posSpells);
                        bard.addChoose(choice);
                        bard.addAmountAdditionalSpells(2);
                    }
                    case VALOR -> bard.addFeatures(EXTRA_ATTACK);
                }
            }
            case 7 -> {
                bard.addSpellSlot(4, 1);
                bard.setAmountPreparedSpells(10);
            }
            case 8 -> {
                abilitySkillImprovement(bard);
                bard.addSpellSlot(4, 2);
                bard.setAmountPreparedSpells(11);
            }
            case 9 -> {
                bard.setProfBonus(4);
                bard.addFeatureAtIndex(SONG_OF_REST_D8, bard.getFeatures().indexOf(SONG_OF_REST_D6));
                bard.removeFeature(SONG_OF_REST_D6);
                bard.addSpellSlot(4, 3);
                bard.addSpellSlot(5, 1);
                bard.setAmountPreparedSpells(12);
            }
            case 10 -> {
                bard.addFeatureAtIndex(BARDIC_INSPIRATION_D10, bard.getFeatures().indexOf(BARDIC_INSPIRATION_D8));
                bard.removeFeature(BARDIC_INSPIRATION_D8);
                expertise(bard, 2);
                bard.addFeatures(MAGICAL_SECRETS);
                Choice choice = new Choice();
                choice.setName(MAGICAL_SECRETS);
                choice.setAmount(2);
                ArrayList<String> posSpells = new ArrayList<>();
                posSpells.addAll(List.of(ALL_CANTRIPS));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_1));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_2));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_3));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_4));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_5));

                removeKnownSpells(bard,choice,posSpells);
                bard.addChoose(choice);
                bard.addAmountAdditionalSpells(2);

                bard.addSpellSlot(5,2);
                bard.setAmountPreparedSpells(14);
            }
            case 11 -> {
                bard.addSpellSlot(6,1);
                bard.setAmountPreparedSpells(15);
            }
            case 12 -> {
                abilitySkillImprovement(bard);
            }
            case 13 -> {
                bard.setProfBonus(5);
                bard.addFeatureAtIndex(SONG_OF_REST_D10,bard.getFeatures().indexOf(SONG_OF_REST_D8));
                bard.removeFeature(SONG_OF_REST_D8);
                bard.addSpellSlot(7,1);
                bard.setAmountPreparedSpells(16);
            }
            case 14 -> {
                Choice choice = new Choice();
                choice.setName(MAGICAL_SECRETS);
                choice.setAmount(2);
                ArrayList<String> posSpells = new ArrayList<>();
                posSpells.addAll(List.of(ALL_CANTRIPS));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_1));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_2));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_3));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_4));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_5));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_6));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_7));

                removeKnownSpells(bard,choice,posSpells);
                bard.addChoose(choice);
                bard.addAmountAdditionalSpells(2);

                switch (bard.getSubclass()) {
                    case LORE -> bard.addFeatures(PEERLESS_SKILL);
                    case VALOR -> bard.addFeatures(BATTLE_MAGIC);
                }
                bard.setAmountPreparedSpells(18);
            }
            case 15 -> {
                bard.addFeatureAtIndex(BARDIC_INSPIRATION_D12,bard.getFeatures().indexOf(BARDIC_INSPIRATION_D10));
                bard.removeFeature(BARDIC_INSPIRATION_D10);
                bard.addSpellSlot(8,1);
                bard.setAmountPreparedSpells(19);
            }
            case 16 -> abilitySkillImprovement(bard);
            case 17 -> {
                bard.setProfBonus(6);
                bard.addFeatureAtIndex(SONG_OF_REST_D12,bard.getFeatures().indexOf(SONG_OF_REST_D10));
                bard.removeFeature(SONG_OF_REST_D10);
                bard.addSpellSlot(9,1);
                bard.setAmountPreparedSpells(20);
            }
            case 18 -> {
                Choice choice = new Choice();
                choice.setName(MAGICAL_SECRETS);
                choice.setAmount(2);
                ArrayList<String> posSpells = new ArrayList<>();
                posSpells.addAll(List.of(ALL_CANTRIPS));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_1));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_2));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_3));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_4));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_5));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_6));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_7));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_8));
                posSpells.addAll(List.of(ALL_SPELLS_LVL_9));

                removeKnownSpells(bard,choice,posSpells);
                bard.addChoose(choice);
                bard.addAmountAdditionalSpells(2);
                bard.addSpellSlot(5,3);
                bard.setAmountPreparedSpells(22);
            }
            case 19 -> {
                abilitySkillImprovement(bard);
                bard.addSpellSlot(6,2);
            }
            case 20 -> {
                bard.addFeatures(SUPERIOR_INSPIRATION);
                bard.addSpellSlot(7,2);
            }
        }
    }

    private void updateCleric(Cleric cleric) {
        switch (cleric.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateDruid(Druid druid) {
        switch (druid.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateFighter(Fighter fighter) {
        switch (fighter.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateMonk(Monk monk) {
        switch (monk.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updatePaladin(Paladin paladin) {
        switch (paladin.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateRanger(Ranger ranger) {
        switch (ranger.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateRouge(Rogue rogue) {
        switch (rogue.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateSorcerer(Sorcerer sorcerer) {
        switch (sorcerer.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateWarlock(Warlock warlock) {
        switch (warlock.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

    private void updateWizard(Wizard wizard) {
        switch (wizard.getLevel()) {
            case 0 -> {
            }
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 -> {
            }
            case 8 -> {
            }
            case 9 -> {
            }
            case 10 -> {
            }
            case 11 -> {
            }
            case 12 -> {
            }
            case 13 -> {
            }
            case 14 -> {
            }
            case 15 -> {
            }
            case 16 -> {
            }
            case 17 -> {
            }
            case 18 -> {
            }
            case 19 -> {
            }
            case 20 -> {
            }
        }
    }

}
