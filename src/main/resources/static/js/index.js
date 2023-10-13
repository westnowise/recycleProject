import { GAME_STATUS, CARD_DATA, MESSAGE } from './const/index.js';
import { renderTimer, renderCard } from './game/view.js';
import {$, $All} from './utils/dom.js';
import { initialState, generateRandomCardArr } from './game/game.js';
import { 
    showFrontBackAnimation, 
    showFrontAnimation, 
    showBackAnimation 
} from './animation/index.js';
import { 
    isSuccess, 
    isTimeout, 
    isGameStart,
    isClickable, 
    isCanPlay, 
    isMatched 
} from './game/check.js';

export default class Game {
    clicked = []
    timerId 
    constructor ({ status, timer, cardList, score }) {
        this.status = status
        this.timer = timer
        this.cardList = cardList
        this.score = score
        this.init()
        this.initEvent()
    }
    
    init () {
        renderTimer(this.timer);
        this.cardList = generateRandomCardArr([...CARD_DATA, ...CARD_DATA]);
        renderCard(this.cardList);
    }

    initEvent () {
        $('.start-button').addEventListener('click', this.startBtnClickHandler);
        $('.card-container').addEventListener('click', this.cardClickHandler);
    }

    startBtnClickHandler = (e) => {
        e.target.disabled = true;
        this.startGame();
    }

    cardClickHandler = async(e) => {
        if (!isGameStart(this.status)) return;
        if (!isClickable(e.target, this.clicked, this.cardList)) return;
        
        const $card = e.target.closest('.card-wrapper');
        await this.flipCardToFront($card);
        
        if (!isCanPlay(this.clicked, $card)) return;

        this.play();
    }

    async startGame () {
        const cards = $All('.card-wrapper');
        await showFrontBackAnimation(cards);
        this.status = GAME_STATUS.START;
        this.startTimer();
    }

    startTimer () {
        this.timerId = setInterval(() => {           
            if (!isTimeout(this.timer)) {
                this.timer -= 1;
                renderTimer(this.timer);
                return;
            }
            this.gameover();
        }, 1000);
    }

    clearTimer () {
        clearInterval(this.timerId);
    }
    
    gameover () {
        this.status = GAME_STATUS.LOSE;
        this.clearTimer();
        alert(MESSAGE.TIME_OUT);
        
        return location.reload();
    }

    gameSuccess () {
        this.status = GAME_STATUS.WIN;
        this.clearTimer();
        alert(MESSAGE.SUCCESS);

        return location.reload();
    }

    flipCardToFront (card) {
        this.cardList[card.dataset.idx].isOpen = true;
        this.clicked.push(card);

        return showFrontAnimation(card);
    }

    async flipCardToBack () {
        await showBackAnimation(this.clicked);

        for(let card of this.clicked) {
            this.cardList[card.dataset.idx].isOpen = false;
        }
        this.clicked = [];
    }

    play () {
        if (!isMatched(this.clicked)) {
            return this.flipCardToBack();
        }
        this.score += 1;

        if (isSuccess(this.timer, this.score)) {
            return this.gameSuccess();
        }
        this.clicked = [];
    }
}

new Game(initialState);