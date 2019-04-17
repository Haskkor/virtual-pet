# virtual-pet

## Functionalities

* Create a pet
* Request a status of the pet
* Do an action on the pet
* Require a visual of the pet

## Pet specs

* Needs to be fed
    * Different kinds of food will imply potential diseases
    * Food can make pet happy but will make it fat and eventually make it sick
* Can be sick and need to be cured
    * Different diseases imply different cures
    * Using the wrong cure can lead to addiction
* Needs to be cleaned
    * Will get diseases if not clean 
* Will age and get different traits
* Needs to be entertained
* Emotion gauge
    * Can be petted
    * Can be grounded
        * If angry just loose the anger
        * If not angry will loose happiness
* Has to be put to sleep
    * Light off/on
* Can get fat of too much food
* Can get depressed
    * Leads to sickness
    
## V1

* Healing when not sick only leads to a lost of happiness
* Only 2 kinds of food. One is making the pet fatter but happier

## To define

~~Food Threshold           ->       Decrease rate~~
~~Happiness Threshold      ->       Decrease rate~~
~~Cleanliness Threshold    ->       Decrease rate~~
~~Growth rate~~
~~Age changes on rates~~
~~Sickness rate~~

## Values

* Egg to hatch            ->       1hour
* Kid to Teenager         ->       2 days 
* Teenager to Adult       ->       3 days
* Adult to Old            ->       4 days
* Old to Dead             ->       2 days

* Weight                  ->       10
    * Loose 1 every 30 minutes if hunger is >= 3
    * Gain 1 for each meal
    * Gain 2 for each candy
    * TODO MAKE THE MATHS TO KNOW WHAT WOULD BE THE THRESHOLDS BY AGE

* Base score happiness    ->       3
    * Increase by 1 when petted or played with
    * Decrease by 1 when grounded
    * Decrease by 1 when hunger is at 3 or below every 30 minutes
    * Decrease by 1 when sickness is at 3 or higher every 30 minutes
    * Decreased by 1 every hour
        * Every 30 minutes when baby or old
* Base score sickness     ->       0
    * Every 30 minutes spent with dejection increase by 1 
        * 2 when baby or old
    * At 2 needs healing
    * At 4 needs healing and rest
    * Dead at 5
* Base score hunger       ->       2
    * Increase by 1 every hour
        * Every 30 minutes when baby or old
    * Hungry at 3
        * Decrease happiness fast  
        * Starts decreasing weight
            * -1 every 30 minutes
    * Starving at 2
        * Depression and other sickness
        * Sickness won't go away by eating, must be cured
    * Dead at 0
    
* Sleep at
    * Kid               ->      7
    * Teenager          ->      9 
    * Adult             ->      8
    * Old               ->      7
* Wake up at
    * Kid               ->      7
    * Teenager          ->      10
    * Adult             ->      6
    * Old               ->      8
