# virtual-pet

lein uberjar
java -jar target/uberjar/virtual-pet-0.1.0-SNAPSHOT-standalone.jar
docker build -t jfarnault/virtual-pet .
docker images
docker system prune
docker image xxx xxx
docker image prune
docker image prune -a
docker run --rm -p 4000:4000 jfarnault/virtual-pet

## Functionalities

* Create a pet
* Request a status of the pet
* Do an action on the pet
* Require a visual of the pet

## V1

* SICKNESS
    * Healing when not sick only leads to a lost of happiness
* HUNGER
    * Only 2 kinds of food. One is making the pet fatter but happier
    * Food can make pet happy but will make it fat and eventually make it sick
* SICKNESS
    * Needs to be cured
    * If healed if not sick will loose happiness
* DIRTINESS
    * Needs to be cleaned
    * Will get diseases if not clean 
* HAPPINESS
    * Can be pet
    * Can be grounded
        * If angry just loose the anger
        * If not angry will loose happiness
    * Can get depressed
        * Leads to sickness
* LIGHTS
    * Lights off if not sleeping decrease happiness fast
    * Lights on during sleep decrease happiness slowly
    * Lights turn on automatically when waking up

## V2

* HUNGER
    * More kind of food
    * Different kinds of food will imply potential diseases
* SICKNESS
    * Different diseases imply different cures
    * Using the wrong cure can lead to addiction
* AGE
    * Get different traits
* HAPPINESS
    * Needs to be entertained

## Timing and Values

* AGE
    * Egg to hatch            ->       1 hour
    * Kid to Teenager         ->       2 days 
    * Teenager to Adult       ->       3 days
    * Adult to Old            ->       4 days
    * Old to Dead             ->       2 days
* WEIGHT
    * Weight                  ->       10
        * Loose 1 every 30 minutes if hunger is >= 3
        * Gain 1 for each meal
        * Gain 2 for each candy
        * __TODO MAKE THE MATHS TO KNOW WHAT WOULD BE THE THRESHOLDS BY AGE__
* HAPPINESS
    * Base score happiness    ->       3
        * Increase by 1 when petted or played with
        * Decrease by 1 when grounded
        * Decrease by 1 when hunger is at 3 or below every 30 minutes
        * Decrease by 1 when sickness is at 3 or higher every 30 minutes
        * Decreased by 1 every 2 hour
            * Every 30 minutes when baby or old
        * Decrease by 1 every hour if the light is not off when sleeping
        * Decrease by 2 every 30 minutes if the light is off when awake
* SICKNESS
    * Base score sickness     ->       0
        * Every 30 minutes spent with dejection increase by 1 
            * 2 when baby or old
        * At 2 needs healing
        * At 4 needs healing and rest
        * Dead at 5
* HUNGER
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
* LIGHTS
    * Sleep at
        * Kid                 ->      7
        * Teenager            ->      9 
        * Adult               ->      8
        * Old                 ->      7
    * Wake up at
        * Kid                 ->      7
        * Teenager            ->      10
        * Adult               ->      6
        * Old                 ->      8
