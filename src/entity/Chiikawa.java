package entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chiikawa {
    private String name;
    private int nowHealth;
    private int level;
    private int baseHealth;
    private int currentHealth;
    private List<Skill> skills;
    private BufferedImage image;
    private Integer evolveTo;
    private Integer evolveLevel;
    private int experience;
    private int maxExperience;
    private boolean animationInProgress;
    private boolean animationAfterProgress;
    private List<LearnableSkill> learnableSkills;
    private String type;
    private double prob;
    private boolean evolving;
    private int evolutionAnimationCounter;
    private int evolutionAnimationDuration;

    public Chiikawa(String name, int baseHealth, List<Skill> skills, String imagePath, Integer evolveTo, 
                    Integer evolveLevel, List<LearnableSkill> learnableSkills, String type, int level, double prob) {
        this.name = name;
        this.nowHealth = baseHealth;
        this.level = level;
        this.baseHealth = calculateBaseHealth(this.level);
        this.currentHealth = this.baseHealth;
        this.skills = skills;
        this.image = loadImage(imagePath);
        this.evolveTo = evolveTo;
        this.evolveLevel = evolveLevel;
        this.experience = 0;
        this.maxExperience = calculateMaxExperience(this.level);
        this.animationInProgress = false;
        this.animationAfterProgress = false;
        this.learnableSkills = learnableSkills;
        this.type = type;
        this.prob = prob;
    }

    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int calculateBaseHealth(int level) {
        return (int) Math.floor(this.nowHealth * (1 + 0.1 * (level - 1)));
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        if (this.evolving) {
            int flashFrequency = 10;
            if (Math.floor(this.evolutionAnimationCounter / flashFrequency) % 2 == 0) {
                g.drawImage(this.image, x, y, width, height, null);
            }
            this.evolutionAnimationCounter++;
            if (this.evolutionAnimationCounter > this.evolutionAnimationDuration) {
                this.evolving = false;
                this.evolutionAnimationCounter = 0;
            } else if (this.evolutionAnimationCounter > this.evolutionAnimationDuration - 500) {
                //this.evolve();
                g.drawImage(this.image, x, y, width, height, null);
                this.animationAfterProgress = true;
            }
        } else {
            g.drawImage(this.image, x, y, width, height, null);
        }
    }

    /*public void evolve() {
        if (this.evolveTo != null && this.level >= this.evolveLevel) {
            Chiikawa evolvedMonsterData = monsterDictionary.get(this.evolveTo);
            if (evolvedMonsterData != null) {
                this.name = evolvedMonsterData.name;
                this.nowHealth = evolvedMonsterData.nowHealth;
                this.baseHealth = calculateBaseHealth(this.level);
                this.currentHealth = evolvedMonsterData.nowHealth;
                this.skills = evolvedMonsterData.skills;
                this.image = evolvedMonsterData.image;
                this.evolveTo = evolvedMonsterData.evolveTo;
                this.evolveLevel = evolvedMonsterData.evolveLevel;
            }
        }
    }*/

    /*public void evolveAnimation() {
        this.evolving = true;
        this.evolutionAnimationCounter = 0;
        this.evolutionAnimationDuration = 700;
        this.animationInProgress = true;

        new Thread(() -> {
            while (this.evolving) {
                try {
                    Thread.sleep(1000 / 60); // 60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.animationInProgress = false;
            this.animationAfterProgress = false;
        }).start();
    }*/

    public void gainExperience(int exp) {
        this.experience += exp;
        if (this.experience >= this.maxExperience) {
            levelUp();
        }
    }

    public boolean hasSkill(String skillName) {
        return this.skills.stream().anyMatch(skill -> skill.getName().equals(skillName));
    }

    public void levelUp() {
        this.level++;
        this.experience = 0;
        this.maxExperience = calculateMaxExperience(this.level);
        this.baseHealth = calculateBaseHealth(this.level);

        for (LearnableSkill skill : this.learnableSkills) {
            if (skill.getLevel() <= this.level) {
                learnSkill(skill.getSkill());
            }
        }

        /*if (this.evolveTo != null && this.level >= this.evolveLevel) {
            evolveAnimation();
        }*/
    }

    public void learnSkill(Skill newSkill) {
        if (hasSkill(newSkill.getName())) {
            return;
        }

        if (this.skills.size() < 4) {
            this.skills.add(newSkill);
        } else {
            int skillToReplaceIndex = 0; // Replace this with the player's choice
            this.skills.set(skillToReplaceIndex, newSkill);
        }
    }

    private int calculateMaxExperience(int level) {
        return (int) Math.floor(50 * Math.pow(level, 1.2));
    }

    public boolean isAlive() {
        return this.currentHealth > 0;
    }

    // Sample Skill class
    public static class Skill {
        private String name;
        private int power;
        private int accuracy;
        private String type;
        private String target;
        private String imagePath;

        public Skill(String name, int power, int accuracy, String type, String target, String imagePath) {
            this.name = name;
            this.power = power;
            this.accuracy = accuracy;//命中率
            this.type = type;
            this.target = target;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }
    }

    // Sample LearnableSkill class
    public static class LearnableSkill {
        private int level;
        private Skill skill;

        public LearnableSkill(int level, Skill skill) {
            this.level = level;
            this.skill = skill;
        }

        public int getLevel() {
            return level;
        }

        public Skill getSkill() {
            return skill;
        }
    }

    private static Map<Integer, Chiikawa> monsterDictionary = new HashMap<>();

    public static void main(String[] args) {
        Skill skill1 = new Skill("Fireball", 20, 20, "fire", "enemy", "assets/images/skills/fireball.png");
        Skill skill2 = new Skill("Leaf knife", 20, 20, "grass", "enemy", "assets/images/skills/leafknife.png");
        Skill skill3 = new Skill("Thunder Shock", 25, 20, "electric", "enemy", "assets/images/skills/thundershock.png");
        Skill skill4 = new Skill("Bubble", 20, 20, "water", "enemy", "assets/images/skills/bubble.png");
        Skill skill5 = new Skill("SandAttack", 20, 20, "ground", "enemy", "assets/images/skills/sandattack.png");

        Skill skill6 = new Skill("FirePunch", 40, 10, "fire", "enemy", "assets/images/skills/firepunch.png");
        Skill skill7 = new Skill("Razor Leaf", 40, 10, "grass", "enemy", "assets/images/skills/razorleaf.png");
        Skill skill8 = new Skill("Thunder Punch", 40, 5, "electric", "enemy", "assets/images/skills/thunderpunch.png");
        Skill skill9 = new Skill("Water Gun", 40, 8, "water", "enemy", "assets/images/skills/watergun.png");
        Skill skill10 = new Skill("Sand Tomb", 40, 8, "ground", "enemy", "assets/images/skills/sandtomb.png");

        Skill skill11 = new Skill("Flamethrower", 60, 5, "fire", "enemy", "assets/images/skills/flamethrower.png");
        Skill skill12 = new Skill("Leaf Blade", 60, 8, "grass", "enemy", "assets/images/skills/leafblade.png");
        Skill skill13 = new Skill("Thunderbolt", 60, 8, "electric", "enemy", "assets/images/skills/thunderbolt.png");
        Skill skill14 = new Skill("Water Pulse", 60, 8, "water", "enemy", "assets/images/skills/waterpulse.png");
        Skill skill15 = new Skill("Scorching Sands", 60, 5, "ground", "enemy", "assets/images/skills/scorchingsands.png");

        Skill skill16 = new Skill("Blast Burn", 80, 8, "fire", "enemy", "assets/images/skills/blastburn.png");
        Skill skill17 = new Skill("Leaf Storm", 80, 8, "grass", "enemy", "assets/images/skills/leafstorm.png");
        Skill skill18 = new Skill("Thunder", 80, 5, "electric", "enemy", "assets/images/skills/thunder.png");
        Skill skill19 = new Skill("Wave Crash", 80, 8, "water", "enemy", "assets/images/skills/wavecrash.png");
        Skill skill20 = new Skill("Earthquake", 80, 8, "ground", "enemy", "assets/images/skills/earthquake.png");

        List<LearnableSkill> chiikawaLearnableSkills = new ArrayList<>();
        chiikawaLearnableSkills.add(new LearnableSkill(5, skill19));

        List<LearnableSkill> hachiwareLearnableSkills = new ArrayList<>();
        hachiwareLearnableSkills.add(new LearnableSkill(13, skill15));

        List<LearnableSkill> rabbitLearnableSkills = new ArrayList<>();
        rabbitLearnableSkills.add(new LearnableSkill(17, skill11));

        monsterDictionary.put(1, new Chiikawa("Chiikawa", 100, new ArrayList<>(Arrays.asList(skill4)), 
                        "assets/images/monsters/Chiikawa.jpg", 2, 10, chiikawaLearnableSkills, "water", 1, 0.5));
        monsterDictionary.put(2, new Chiikawa("Hachiware", 115, new ArrayList<>(Arrays.asList(skill5, skill10)), 
                        "assets/images/monsters/Hachiware.gif", 3, 15, hachiwareLearnableSkills, "ground", 10, 0.2));
        monsterDictionary.put(3, new Chiikawa("Rabbit", 125, new ArrayList<>(Arrays.asList(skill1, skill6, skill16)), 
                        "assets/images/monsters/Rabbit.jpg", null, null, rabbitLearnableSkills, "fire", 15, 0.05));
    }
}
