// ********************************************************************
        // $('logo_text').circleType({radius:384,dir:-1});
        new CircleType(document.getElementById("logo_text")).dir(-1).radius(384);

// ********************************************************************

     const hoverTexts = document.querySelectorAll('.hover_text');
     const hoverImgs = document.querySelectorAll('.hover_img');

     for (let i = 0; i < hoverTexts.length; i++) {
        hoverTexts[i].addEventListener('mouseover', function() {
        hoverImgs[i].classList.add('hover_img_active');
        });

        hoverTexts[i].addEventListener('mouseout', function() {
        hoverImgs[i].classList.remove('hover_img_active');
         });
    }

// ********************************************************************
		let counter = 0;
		let faviconImgs = 48;
		let favicons = document.querySelectorAll('link');

		function animateFavicon() {
			favicons.forEach(item => {
				if (item.getAttribute('rel').indexOf('icon') >= 0) {
					item.setAttribute('href', '../Icons/fav/' + counter + '.gif');
				}
			});
			counter++;
			if (counter == faviconImgs) { counter = 1; }
		}

		setInterval(animateFavicon, 100);

        const dropdownToggle = document.querySelector('.dropdown-toggle');
        const closeIcon = document.querySelector('.close-icon');
        const dropdownContent = document.querySelector('.dropdown-content');
        let timerId;

        dropdownToggle.addEventListener('mouseenter', function() {
          if (dropContent) {
            dropContent.style.display = 'none';
          }
          if (dropdownContent) {
            dropdownContent.style.display = 'block';
          }
        });

// ********************************************************************
            dropdownContent.addEventListener('mouseout', function() {
                timerId = setTimeout(function() {
                dropdownContent.style.display = 'none';
                }, 1000);
            });

            dropdownContent.addEventListener('mouseover', function() {
                clearTimeout(timerId);
                dropdownContent.style.display = 'block';
            });

            closeIcon.addEventListener('click', function() {
                dropdownContent.style.display = 'none';
             });

        const dropToggle = document.querySelector('.drop-toggle');
        const closeIco = document.querySelector('.close-ico');
        const dropContent = document.querySelector('.drop-content');
        const trashIcon = document.querySelector('.trash_icon');
        let timer;

        dropToggle.addEventListener('mouseenter', function() {
          if (dropdownContent) {
            dropdownContent.style.display = 'none';
          }
          if (dropContent) {
            dropContent.style.display = 'block';
          }
        });



        if(dropContent){
            dropContent.addEventListener('mouseout', function() {
                timer = setTimeout(function() {
                dropContent.style.display = 'none';
                }, 1000);
            });


            dropContent.addEventListener('mouseover', function() {
                clearTimeout(timer);
                dropContent.style.display = 'block';
            });

         }

        if(closeIco){
            closeIco.addEventListener('click', function() {
                dropContent.style.display = 'none';
             });

            trashIcon.addEventListener('click', function() {
                dropContent.style.display = 'none';
            });
        }

// ********************************************************************