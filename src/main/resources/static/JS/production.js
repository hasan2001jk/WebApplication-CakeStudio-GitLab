const filter_position_one = document.querySelector('.filter_position_one');
const filter_position_two = document.querySelector('.filter_position_two');
const filter_position_three = document.querySelector('.filter_position_three');

filter_position_two.addEventListener('click', () => {
        const production_wrap = document.querySelector('.production_wrap');
        const production_card = document.querySelectorAll('.production_card');
        const production_card_img = document.querySelectorAll('.production_card_img');
        const img = document.querySelectorAll('.production_card_img>img');
        const production_card_title = document.querySelectorAll('.production_card_title');
        const production_card_price = document.querySelectorAll('.production_card_price');
        const production_card_wrap_img = document.querySelectorAll('.production_card_wrap_img');
        const production_card_heart = document.querySelectorAll('.production_card_heart');
        
        if(production_card_title[0].querySelector('form')!==null){
            
            production_card_title.forEach(pr_t => {
                pr_t.removeChild(pr_t.querySelector('form'));
                pr_t.querySelector('.production_card_wrap_img').style.display='flex';
                pr_t.style.width='100%';
            });
        }
        
        if(production_card_title[0].style.width =='auto'){
            production_card_title.forEach( prod_card_title => {
                prod_card_title.style.width='100%';
            });
        }

        

        production_wrap.style.flexDirection='column';
        production_card_wrap_img.forEach(card_wrap_img => {
            card_wrap_img.style.width='100px';
            card_wrap_img.style.alignItems='flex-start';
            card_wrap_img.style.margin='0';
        }); 
    
        production_card.forEach(card => {
            card.style.flexBasis = '100%';
            card.style.flexDirection = 'row';
            card.style.margin='0';
        });
    
        production_card_img.forEach(card_img => {
            card_img.style.flexShrink='0';
        });
    
        for(let i =0; i<production_card_title.length;i++){
            production_card_title[i].appendChild(production_card_price[i]);
            production_card_title[i].appendChild(production_card_wrap_img[i]);
            production_card_title[i].querySelector('p').style.marginBottom='20px';
        }



    });

    filter_position_one.addEventListener('click', ()=>{
            // Получаем элемент, который должен быть обернут
            if(document.querySelectorAll('.production_card_title')[0].querySelector('form')!==null){
                document.querySelectorAll('.production_card_title').forEach(card => {
                    card.removeChild(card.querySelector('form'));
                });
            }

            let productionWrap = document.querySelector(".production_wrap");

            // Добавляем стили к элементу
            productionWrap.style.width = "100%";
            productionWrap.style.height = "100%";
            productionWrap.style.display = "flex";
            productionWrap.style.flexDirection = "row";
            productionWrap.style.justifyContent = "space-between";
            productionWrap.style.gap = "40px";
            productionWrap.style.flexWrap = "wrap";
            productionWrap.style.padding = "20px";

            // Получаем все элементы с классом "production_card"
            let productionCards = document.querySelectorAll(".production_card");

            // Добавляем стили к каждому элементу
            productionCards.forEach((card) => {
            card.style.flexBasis = "200px";
            card.style.height = "100%";
            card.style.boxShadow = "0px 4px 4px 0px rgba(0, 0, 0, 0.25)";
            card.style.display = "flex";
            card.style.flexDirection = "column";
            card.style.justifyContent = "space-around";

            // Получаем дочерние элементы каждого "production_card"
            let cardImg = card.querySelector(".production_card_img");
            let cardTitle = card.querySelector(".production_card_title");
            let cardPrice = card.querySelector(".production_card_price");
            let cardWrapImg = card.querySelector(".production_card_wrap_img");
            let cardBasket = card.querySelector(".production_card_basket");
            let cardHeart = card.querySelector(".production_card_heart");

            card.appendChild(cardPrice);
            card.appendChild(cardWrapImg);
            // Добавляем стили к дочерним элементам
            cardImg.style.height = "150px";
            cardImg.style.marginBottom = "10px";
            cardImg.style.marginRight = "10px";
            cardImg.style.marginLeft = "10px";
            //- cardImg.querySelector("img").style.width = "100%";
            cardImg.querySelector("img").style.height = "100%";

            cardTitle.style.fontFamily = "'Libre Baskerville', serif";
            cardTitle.style.fontSize = "18px";
            cardTitle.style.fontWeight = "300";
            cardTitle.style.textAlign = "left";
            cardTitle.style.width = "100%";
            cardTitle.style.marginBottom = "5px";
            cardTitle.style.marginLeft = "10px";

            cardPrice.style.fontSize = "20px";
            cardPrice.style.fontWeight = "bold";
            cardPrice.style.textAlign = "left";
            cardPrice.style.width = "100%";
            cardPrice.style.marginBottom = "15px";
            cardPrice.style.marginLeft = "10px";

            cardWrapImg.style.width = "100%";
            cardWrapImg.style.display = "flex";
            cardWrapImg.style.flexDirection = "row";
            cardWrapImg.style.justifyContent = "space-between";
            cardWrapImg.style.alignItems = "center";
            cardWrapImg.style.marginBottom = "20px";

            cardBasket.style.marginLeft = "10px";
            cardHeart.style.marginRight = "10px";
            });
        });


        filter_position_three.addEventListener('click', () => {
        const production_wrap = document.querySelector('.production_wrap');
        const production_card = document.querySelectorAll('.production_card');
        const production_card_img = document.querySelectorAll('.production_card_img');
        const img = document.querySelectorAll('.production_card_img>img');
        const production_card_title = document.querySelectorAll('.production_card_title');
        const production_card_price = document.querySelectorAll('.production_card_price');
        const production_card_wrap_img = document.querySelectorAll('.production_card_wrap_img');
        const production_card_heart = document.querySelectorAll('.production_card_heart');
        
        production_wrap.style.flexDirection='column';
        production_card_wrap_img.forEach(card_wrap_img => {
            card_wrap_img.style.width='100px';
            card_wrap_img.style.alignItems='flex-start';
            card_wrap_img.style.margin='0';
        }); 
    
        production_card.forEach(card => {
            card.style.flexBasis = '100%';
            card.style.flexDirection = 'row';
            card.style.justifyContent='space-between';
            card.style.margin='0';
        });
    
        production_card_img.forEach(card_img => {
            card_img.style.flexShrink='0';
        });
    
        for(let i =0; i<production_card_title.length;i++){
            production_card_title[i].style.width='200px';
            production_card_title[i].style.whiteSpace='wrap';
            production_card_title[i].style.marginRight='15px';
            production_card_title[i].style.marginTop='15px';
            production_card_title[i].appendChild(production_card_price[i]);
            production_card_title[i].appendChild(production_card_wrap_img[i]);
            production_card_title[i].querySelector('p').style.marginBottom='20px';
        }

        if(production_card_title[0].querySelector('form')!==null){
            production_card_title.forEach(cr_t => {
                cr_t.insertBefore(cr_t.querySelector('.production_card_price'), cr_t.querySelector('form'));
            });
        }else{
            production_card_title.forEach(pr_title => {
                pr_title.querySelector('.production_card_wrap_img').style.display='none';
                const form = document.createElement('form');
                form.setAttribute('method', 'POST');
                form.setAttribute('action', '');
    
                const label = document.createElement('label');
                label.setAttribute('for', 'quantity');
                label.textContent = 'Кол-во:';
    
                const input = document.createElement('input');
                input.setAttribute('type', 'number');
                input.setAttribute('id', 'quantity');
                input.setAttribute('name', 'quantity');
                input.setAttribute('min', '1');
                input.setAttribute('max', '5');
                input.setAttribute('value', '1');
    
                const submitButton = document.createElement('input');
                submitButton.setAttribute('type', 'submit');
                submitButton.setAttribute('value', 'В корзину');
    
                form.appendChild(label);
                form.appendChild(input);
                form.appendChild(submitButton);
                pr_title.appendChild(form);
    
                form.style.width='100%';
                form.style.display='flex';
                form.style.flexDirection='row';
                form.style.justifyContent='space-between';
                form.style.alignItems='center';
                label.style.fontSize='14px';
                input.style.maxWidth='30px';
                input.style.maxHeight='35px';
                input.style.backgroundColor='transparent';
                //- input.style.paddingRight='20px';
                input.style.border='1px solid #d9d9d9';
                input.style.borderRadius='3px';
                input.style.paddingLeft='0px';
                input.style.paddingTop='7px';
                input.style.paddingBottom='7px';
                input.style.fontSize='13px';
                input.style.lineHeight='15px';
                input.style.textAlign='center';
    
    
                submitButton.style.maxWidth='100px';
                submitButton.style.paddingLeft='0px';
                submitButton.style.textAlign='center';
            });
            
        }
        


    });