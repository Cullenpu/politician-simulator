package com.example.politicgame.GamesActivity.StampGame;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Game handler for Stamp Game It is in charge of updating the score and storing the score for
 * the stamp game that is going on
 */
class StampGameHandler {
  /** The list of verbs being used in game handler to construct Prompts */
  private List<Word> verbs;

  /** The list of nouns being used in game handler to construct Prompts */
  private List<Word> nouns;

  /** The current score of the user, it is being displayed by TextView as well */
  private int currentScore;

  /** The list of Prompts (ie. Proposals) that has been given to the user */
  private List<Proposal> prompts;

  /** The current Prompt being given to the user */
  private Proposal currentPrompt;

  /**
   * The pronoun used to call the user, we will modify this in Phase 2 where depending on the
   * character chosen by the user, the pronoun will be changed
   */
  private String pronoun = "sir";

  /** The verb used for when we run out of prompts */
  private Verb emptyAction = new Verb("", 0);

  /** The Noun used for when we run out of prompts */
  private Noun emptyNoun = new Noun("", 0, false);

  /** The list of Strings we use to form the beginning of a proposal */
  private List<String> promptBeginList =
      new ArrayList<>(
          Arrays.asList(
              "Good afternoon "
                  + pronoun
                  + ", based on our campaign researchers' speculation, would you like to",
              "This just in "
                  + pronoun
                  + ", based on the rumors from our excited supporters, is it true that you would",
              "Greetings " + pronoun + ", based on the meeting result last week, are you going to",
              "Nice to meet you "
                  + pronoun
                  + ". I am one of your many campaign assistants and was wondering if you would advocate to"));

  /**
   * The constructor for the game handler
   *
   * <p>We construct one prompt when we instantiate the stampGameHandler, and we replace the
   * currentPrompt whenever the updateRating() method is called.
   */
  StampGameHandler() {
    verbs = new ArrayList<>();
    nouns = new ArrayList<>();
    prompts = new ArrayList<>();

    // Positive verbs that has a positive effect on the object
    List<String> verbListPositive =
        new ArrayList<>(
            Arrays.asList(
                "campaign with the best interests of",
                "send aid to",
                "donate money to charities that work with",
                "gift a bouquet of flowers to"));

    addWordToList(verbListPositive, verbs, "posVerb");

    // Positive verbs that has a negative effect on the object
    List<String> verbListNegative =
        new ArrayList<>(
            Arrays.asList(
                "send a police squad to detain and imprison",
                "punch the daylights out of",
                "launch nukes against",
                "send an army against",
                "launch an investigation against",
                "personally find and laugh at"));

    addWordToList(verbListNegative, verbs, "negVerb");

    // Positive nouns that are not amountable
    List<String> nounListPositiveNA =
        new ArrayList<>(
            Arrays.asList(
                "Gandhi, a recently popular peace advocate who campaigns in India",
                "the popular late-night TV show host John Olive Oil",
                "Bill Rye, a once popular figure in science who recently published a paper on the benefits of foot rubs",
                "Toe-Knee, a rising star in the genre of Jazz, who recently released his hit album \"CS Blues\"",
                "Jacki, a citizen who has been wrongfully imprisoned in the country of Canada for not holding the door for the boy behind him"));

    addWordToList(nounListPositiveNA, nouns, "posNounNA");

    // Positive nouns that are amountable
    List<String> nounListPositiveYA =
        new ArrayList<>(
            Arrays.asList(
                "puppies, specifically the ones at the Downtown Toronto Dog Shelter",
                "Boundless Peacocks, the very last of their species",
                "sad computer science students at the University of Toronto"));

    addWordToList(nounListPositiveYA, nouns, "posNounYA");

    // Negative nouns that are not amountable
    List<String> nounListNegativeNA =
        new ArrayList<>(
            Arrays.asList(
                "the leader of North Coreeah, who is planning a nuclear strike",
                "Colin, a medical practitioner found to have cheated on his medical exams after a related illegal nose smuggling ring was busted",
                "Kavin, a phantom thief who masterminded the theft all the laptop chargers, but not the laptops, at the University of Toronto last Fall"));

    addWordToList(nounListNegativeNA, nouns, "negNounNA");

    // Negative nouns that are amountable
    List<String> nounListNegativeYA = new ArrayList<>(Arrays.asList("seal clubbers"));

    addWordToList(nounListNegativeYA, nouns, "negNounYA");
  }

  /**
   * generate a double between(inclusive) double min and double max
   *
   * @param min the minimum double we can generate
   * @param max the maximum doulbe we can generate
   * @return a double between(inclusive) min and max
   */
  private double getRandomDoubleBetweenRange(double min, double max) {
    return (Math.random() * ((max - min) + 1)) + min;
  }

  /**
   * Make each word in stringList into a Word object and put it into wordList, the category of the
   * stringList is specified by the String word
   *
   * @param stringList a list of strings
   * @param wordList the list of Word
   * @param word the category for all the strings that will be put into wordList
   */
  private void addWordToList(List<String> stringList, List<Word> wordList, String word) {
    int min = 1;
    int max = 5;

    switch (word) {
      case "posVerb":
        for (String item : stringList) {
          int posCategory = (int) (getRandomDoubleBetweenRange(min, max));
          wordList.add(new Verb(item, posCategory));
        }
        break;
      case "negVerb":
        for (String item : stringList) {
          int negCategory = (int) (getRandomDoubleBetweenRange(min, max));
          wordList.add(new Verb(item, -negCategory));
        }
        break;
      case "negNounYA":
        for (String item : stringList) {
          int negCategory = (int) (getRandomDoubleBetweenRange(min, max));
          wordList.add(new Noun(item, -negCategory, true));
        }
        break;
      case "negNounNA":
        for (String item : stringList) {
          int negCategory = (int) (getRandomDoubleBetweenRange(min, max));
          wordList.add(new Noun(item, -negCategory, false));
        }
        break;
      case "posNounYA":
        for (String item : stringList) {
          int posCategory = (int) (getRandomDoubleBetweenRange(min, max));
          wordList.add(new Noun(item, posCategory, true));
        }
        break;
      case "posNounNA":
        for (String item : stringList) {
          int posCategory = (int) (getRandomDoubleBetweenRange(min, max));
          wordList.add(new Noun(item, posCategory, false));
        }
        break;
    }
  }

  /** Generate a prompt, if the verbs or nouns are empty, we will generate an emptyPrompt */
  private void createPrompt() {
    // For when we have a male or female player

    int verbIndex = (int) (Math.random() * (verbs.size()));
    int nounIndex = (int) (Math.random() * (nouns.size()));

    if (verbs.isEmpty() || nouns.isEmpty()) {
      currentPrompt = new Proposal("", emptyAction, emptyNoun);

    } else {

      String promptBegin = promptBeginList.get((int) (Math.random() * (promptBeginList.size())));

      if (((Noun) nouns.get(nounIndex)).getAmountable()) {
        int amount = (int) (Math.random() * (1000));
        currentPrompt =
            new Proposal(
                promptBegin,
                ((Verb) verbs.remove(verbIndex)),
                ((Noun) nouns.remove(nounIndex)),
                amount);
      } else {
        currentPrompt =
            new Proposal(
                promptBegin, ((Verb) verbs.remove(verbIndex)), ((Noun) nouns.remove(nounIndex)));
      }

      prompts.add(currentPrompt);
    }
  }

  /**
   * Change the npcPrompt to a new prompt
   *
   * @param npcPrompt the TextView object that displays npcPrompt
   */
  void setPrompt(TextView npcPrompt) {
    this.createPrompt();
    String npcProposal = this.getCurrentPrompt().getString();
    npcPrompt.setText(npcProposal);
  }

  private Proposal getCurrentPrompt() {
    return currentPrompt;
  }

  private int getCurrentPromptScore() {
    return currentPrompt.getCategory();
  }

  /** @return the list of prompts in prompts */
  public List<Proposal> getPrompts() {
    return prompts;
  }

  /**
   * Return the integer representation of the text tv is representing
   *
   * @param tv the TextView object
   * @return the integer representation of tv
   */
  Integer intFromTextView(TextView tv) {
    String oldString = tv.getText().toString();
    return Integer.valueOf(oldString.substring(0, oldString.length() - 1));
  }

  private void setCurrentScore(int newScore) {
    currentScore = newScore;
  }

  int getCurrentScore() {
    return currentScore;
  }

  /**
   * Changed the rating depending on the button click
   *
   * @param rating the TextView object that displays rating
   * @param clickedYes the boolean value expressing whether the user pressed yes or no
   */
  void changeRating(TextView rating, boolean clickedYes) {
    Integer currentScore = intFromTextView(rating);
    Integer minScore = 0;
    Integer maxScore = 100;
    int updatedScore;

    Integer scoreChange = this.getCurrentPromptScore();
    if (!clickedYes) {
      updatedScore = currentScore - scoreChange;
    } else {
      updatedScore = currentScore + scoreChange;
    }

    if (currentScore >= 0 && currentScore <= 100) {
      if (updatedScore >= 0 && updatedScore <= 100) {
        updateRating(rating, updatedScore);
      } else if (updatedScore < 0) {
        updateRating(rating, minScore);
      } else {
        updateRating(rating, maxScore);
      }
    }
  }

  /**
   * updates the rating that is being displayed on screen
   *
   * @param rating TextView object of the rating
   * @param ratingInt the new value of the rating
   */
  private void updateRating(TextView rating, Integer ratingInt) {
    String newRating = ratingInt.toString() + '%';
    rating.setText(newRating);
    setCurrentScore(ratingInt);
    Log.i("Stamp Score", ((Integer) getCurrentScore()).toString());
  }

  /**
   * updates the number of proposals left that is being displayed on screen
   *
   * @param proposal TextView object of the proposal
   * @param proposalLeft the integer value of the number of proposal left to be given to the user
   */
  private void updateProposal(TextView proposal, Integer proposalLeft) {
    String proposalLeftString = proposalLeft.toString();
    proposal.setText(proposalLeftString);
  }

  /**
   * change the proposal number displayed to the number of proposals left
   *
   * @param proposalNum the minimum of {size of verbs, size of nouns};
   */
  void changeProposalNum(TextView proposalNum) {
    int currentProposalLeft = Math.min(verbs.size(), nouns.size());
    updateProposal(proposalNum, currentProposalLeft);
  }

  int getPromptsSize(TextView proposalLeft) {
    String oldString = proposalLeft.getText().toString();
    return Integer.valueOf(oldString);
  }
}
