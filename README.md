<a href="http://pgu-geo.appspot.com/"><strong>&#8594; Go to the website</strong></a><br/>
gson, oauth, gwt-bootstrap, objectify

TODOs
=======
* toolbar en haut et sous-toolbar
* public profile
* contacts map
* company profile


linkedin rest:
https://developer.linkedin.com/rest
https://developer.linkedin.com/documents/profile-fields

oauth lib:
https://github.com/fernandezpablo85/scribe-java/wiki/getting-started
http://commons.apache.org/codec/

map api:

https://developers.google.com/maps/documentation/javascript/overlays#InfoWindows
https://developers.google.com/maps/documentation/javascript/overlays#Circles

https://google-developers.appspot.com/maps/documentation/javascript/examples/geocoding-simple
+ country: countrycode
https://developers.google.com/maps/documentation/javascript/
https://developers.google.com/maps/documentation/geocoding/
https://developers.google.com/fusiontables/docs/developers_guide

fusion tables:
ISO_2DIGIT <=> country codes
https://developers.google.com/fusiontables/docs/v1/getting_started#background

83
ca: 3
it: 1
cz: 2
us: 3
td: 1
gb: 3
au: 1
de: 3
fr: 38
ch: 13
ru: 1
es: 9
be: 1

{
  "firstName": "Pascal",
  "headline": "Senior Web Java J2EE Engineer Developer at SFEIR",
  "lastName": "Guilcher",
  "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=6949649&authToken=o2MM&authType=name&trk=api*a196273*s204372*"}
}
HOORAY IT WORKED!!
{
  "_total": 83,
  "values": [
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:AtQo"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/tU7PW4TMdZ"
      },
      "firstName": "David",
      "headline": "Développeur Architecte Artisan J2EE, Agiliste, Manager",
      "id": "tU7PW4TMdZ",
      "industry": "Information Technology and Services",
      "lastName": "Aboulkheir",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_N-oHBbXGL8Z-1Iyp99sBBXA2FCf00eyp91OBBXGO4bRfh2wy4qVXJknrd27hy7grvAH9Z_rV9lGy",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=104566555&authToken=AtQo&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:DYdd"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/19p4HR3pMy"
      },
      "firstName": "Yasmine",
      "headline": "Ingénieur Études et Développement JAVA/J2EE GWT chez SFEIR",
      "id": "19p4HR3pMy",
      "industry": "Computer Software",
      "lastName": "Aite",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_pCD07netVs8C9QXWyT2u7z4g4p5DNQqWY5J87zwDFJljwGidKkj13v0i9tL-cTz5jTf38tz2-dM_",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=35877499&authToken=DYdd&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:8X2m"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/vauS5lrlN3"
      },
      "firstName": "Ainhoa",
      "headline": "Gestor de Proyectos en Batz S. Coop.",
      "id": "vauS5lrlN3",
      "industry": "Mechanical or Industrial Engineering",
      "lastName": "Apesteguía",
      "location": {
        "country": {"code": "es"},
        "name": "Bilbao Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_XGm9LdaPIadqcoWZXkSLLwYgWWDZvoWZQLsWLwSfnuJeEmY4eXYVwIJ3bFSb9DeNL3uHo2PwVc_C",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=29328722&authToken=8X2m&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:i5u9"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/uMAPCr_mlY"
      },
      "firstName": "Sylvie",
      "headline": "Responsable des Ressources Humaines chez SFEIR",
      "id": "uMAPCr_mlY",
      "industry": "Information Technology and Services",
      "lastName": "Belze",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=77301287&authToken=i5u9&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:jIlU"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/wsOzxjfAYM"
      },
      "firstName": "Reynald",
      "headline": "Software Architect at SecuTix SA",
      "id": "wsOzxjfAYM",
      "industry": "Computer Software",
      "lastName": "Borer",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_y_g-snaKJ6G3nFIa-XY3szZPRX6un3EaO3WSszeCX3-yW5gmr5SOqvpuzEQPz6wGg8x2vt9_q6kc",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=15084104&authToken=jIlU&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:OJBq"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/WVP61eMKYX"
      },
      "firstName": "Celine",
      "headline": "SEUR Professional Services Operation Representative at McAfee",
      "id": "WVP61eMKYX",
      "industry": "Retail",
      "lastName": "Briand",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_IP83MDL-l8xLKvS_5q--Mmc-15MdKvS_dcNYM7ka2iSJ7AsibvKDcfrhjeJ91tu7oKC0nH9bPrEI",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=108133630&authToken=OJBq&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:0W4l"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/LgHJbYSoen"
      },
      "firstName": "Amelia",
      "headline": "senior government bonds broker at Kepler",
      "id": "LgHJbYSoen",
      "industry": "Financial Services",
      "lastName": "Carrera",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=10754119&authToken=0W4l&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:saYv"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/nFbHKDmpJ5"
      },
      "firstName": "Jesus",
      "headline": "IT manager",
      "id": "nFbHKDmpJ5",
      "industry": "Computer Software",
      "lastName": "Cobo",
      "location": {
        "country": {"code": "es"},
        "name": "Madrid Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_FiVC9muAv5HNc3j156Jj92jAcbOJRCD15Xaj92mlHCodX6VPwLomZuVxJdY6Z5206h4yJwrAD43P",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=885524&authToken=saYv&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:4NCL"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/QkEDgN5NOU"
      },
      "firstName": "Annabella",
      "headline": "Marketing Director at Team Europe",
      "id": "QkEDgN5NOU",
      "industry": "Marketing and Advertising",
      "lastName": "Da Encarnacao",
      "location": {
        "country": {"code": "de"},
        "name": "Berlin Area, Germany"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_Rm-zuCWnfjx_kYzkRE1ouhWUSUUfXjkkM76EuhHQtY70RMPXBeGJG8ye_zRrbV5eVSrI_b9eVXcd",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=5381182&authToken=4NCL&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:yCcV"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/6IJLo2rCHv"
      },
      "firstName": "Pierre",
      "headline": "Administrateur à Panhard General Defence",
      "id": "6IJLo2rCHv",
      "industry": "Information Services",
      "lastName": "Dalmaz",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=37149582&authToken=yCcV&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:m8rO"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/7yGmN8jh6I"
      },
      "firstName": "Alexis",
      "headline": "Information security architect",
      "id": "7yGmN8jh6I",
      "industry": "Information Technology and Services",
      "lastName": "Davoux",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=12281201&authToken=m8rO&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:S88C"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/4pr7XBPlxl"
      },
      "firstName": "Salvador",
      "headline": "Software Engineer",
      "id": "4pr7XBPlxl",
      "industry": "Information Technology and Services",
      "lastName": "Diaz",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=127861262&authToken=S88C&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:sTvE"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/EFIZSDk9iQ"
      },
      "firstName": "Michel",
      "headline": "Senior Software Engineer at Sysmosoft SA",
      "id": "EFIZSDk9iQ",
      "industry": "Information Technology and Services",
      "lastName": "Dommen",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=12608013&authToken=sTvE&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:SdgE"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/fsWhv0kEbQ"
      },
      "firstName": "Emmanuel",
      "headline": "R&D Engineer at BonitaSoft",
      "id": "fsWhv0kEbQ",
      "industry": "Information Services",
      "lastName": "Duchasténier",
      "location": {
        "country": {"code": "fr"},
        "name": "Grenoble Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_u_fFGJm1LVlVqohs2XHZGsajLsGE9WTsaCRZGse7Vx9sdDnVh50IuVpTIqCvvm8nS8DJDjs5qM9M",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=6682260&authToken=SdgE&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "firstName": "private",
      "id": "private",
      "lastName": "private"
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:GZHK"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/YmOlqP87H9"
      },
      "firstName": "Alain",
      "headline": "Presales and Product Manager at Nuxeo",
      "id": "YmOlqP87H9",
      "industry": "Information Technology and Services",
      "lastName": "Escaffre",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_CHQ4YJ7Xavivw0bD2DbFYU7XmKFzdJ9DhftbYUO4l91L9g3SaanqrRE98gboEOc3_Ik6lp2uI7C_",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=6033923&authToken=GZHK&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:f01x"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/Kgwjn_jl5i"
      },
      "firstName": "Guillermo",
      "headline": "Dynamics CRM Consultant at Avanade",
      "id": "Kgwjn_jl5i",
      "industry": "Information Technology and Services",
      "lastName": "Estévez",
      "location": {
        "country": {"code": "es"},
        "name": "Madrid Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_0hOv4PJOb6Q2qeH8jXY64AIPwLN29DW8g3oo4AdiNh8pdWYhP6aMnlgD5wq1voeuOipwcncXc77W",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=16090091&authToken=f01x&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:1Dt8"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/qcl0HWta7l"
      },
      "firstName": "James",
      "headline": "Customer development executive at Nestle",
      "id": "qcl0HWta7l",
      "industry": "Food Production",
      "lastName": "Fairbairn",
      "location": {
        "country": {"code": "gb"},
        "name": "London, United Kingdom"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_jOgiHAN9zoG_ikO60VypHPiBBEvfi3a6x0WyHPXke7T0p5JQl4SfQ1KEMXzr86fopjxjFqn_72y-",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=111880735&authToken=1Dt8&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:b_8j"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/nHR7g8Awhp"
      },
      "firstName": "Geoffrey",
      "headline": "Team Leader / Manager chez SFEIR",
      "id": "nHR7g8Awhp",
      "industry": "Information Technology and Services",
      "lastName": "Garnotel",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=141157403&authToken=b_8j&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:2-tZ"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/8IqqkNuOUp"
      },
      "firstName": "Didier",
      "headline": "COO and CTO SFEIR",
      "id": "8IqqkNuOUp",
      "industry": "Information Technology and Services",
      "lastName": "Girard",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_M5umgFytNlYDsLK2JXaggkRYNAd3nLA2RkZAgkRawqjAWT6uz_yC1X7hsxWOzGrhZQmKtiWeBE0J",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=158449&authToken=2-tZ&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:PV_w"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/2BKDHFoOZD"
      },
      "firstName": "Pierre",
      "headline": "Tech leader at SFEIR",
      "id": "2BKDHFoOZD",
      "industry": "Information Technology and Services",
      "lastName": "Gosselin",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=7616067&authToken=PV_w&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:1yzG"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/Z7i1Vp-MIf"
      },
      "firstName": "Jessica",
      "headline": "Student Aide at Shaker Heights City School District",
      "id": "Z7i1Vp-MIf",
      "industry": "Education Management",
      "lastName": "Gross",
      "location": {
        "country": {"code": "us"},
        "name": "Cleveland/Akron, Ohio Area"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_5KCgs7Aj05xNlrsCFchasDFp0TRUl9sCFlnGsD8uhFfwmtSGdntlqSqCAuUk-AUmkP8_vEO-n49V",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=148128918&authToken=1yzG&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:KSfA"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/yIVfmsmAgP"
      },
      "firstName": "Samir",
      "headline": "Sr Project Manager & Sr Software Architect",
      "id": "yIVfmsmAgP",
      "industry": "Computer Software",
      "lastName": "Guesmia",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_bNwbMo9j68snOw27LqeNMWrDQ5WNYw27LPx4MWljM8gkTfRfItUwcdL1eIdwjuD_QBWMnafy_IW5",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=7904549&authToken=KSfA&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:uTVC"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/0Qa1Px-4i_"
      },
      "firstName": "Michael",
      "headline": "Vice President at PNC Bank",
      "id": "0Qa1Px-4i_",
      "industry": "Financial Services",
      "lastName": "Hammond",
      "location": {
        "country": {"code": "us"},
        "name": "Cleveland/Akron, Ohio Area"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_n2b0MbSQ0bxlotM7NHQhMXydg_WpEc47NeP8MLfZCLg2vPffVoB1c5UnPmdidrJ_Bf53nCgY22TG",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=1724541&authToken=uTVC&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:cbLx"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/UTX_BrKRC_"
      },
      "firstName": "Eduardo",
      "headline": "Co-Founder at Nazaríes IT",
      "id": "UTX_BrKRC_",
      "industry": "Information Technology and Services",
      "lastName": "Haro",
      "location": {
        "country": {"code": "es"},
        "name": "Madrid Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_9fXpRT2epLxAwBZTnmQfRiDbpCVjwBJTNSr_RiDsibaDN1a3sdqrv_MN-2sGHK4Sc26Gqk0EHvBe",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=85095667&authToken=cbLx&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:FFnW"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/hmTvXDE4Zj"
      },
      "firstName": "Yifeng",
      "headline": "IT Engineer",
      "id": "hmTvXDE4Zj",
      "industry": "Information Technology and Services",
      "lastName": "HE",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_uBxCSytlzJiH1F36a9jjS0cgcRv6PC36azwjS0v_HOT9u69Qhl7mix3SJvzJr5hoSNgyCZFYRpzg",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=31342388&authToken=FFnW&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:ODOg"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/Y-RcljNp4l"
      },
      "firstName": "Olivier",
      "headline": "Software architect at ADEO Services",
      "id": "Y-RcljNp4l",
      "industry": "Information Technology and Services",
      "lastName": "Hedin",
      "location": {
        "country": {"code": "fr"},
        "name": "Lille Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_ncgMlQK0IB4K-Dkpqzu6lXrAwt7rtDzpqvW6lLP3NBUhSWGyVASv05Ff5pffAoqrBqxbYCT5T5VP",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=25803725&authToken=ODOg&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:-zXd"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/jODYH-rgmB"
      },
      "firstName": "Manuel",
      "headline": "Front-End Engineer at ELCA Informatique SA",
      "id": "jODYH-rgmB",
      "industry": "Computer Software",
      "lastName": "Hitz",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_vJv4J_CE3TYgfjwCzVcbJ3K6D_RASYVCc4TbJ3A9-Qf3tVDGJ05qBT54iDUaaMRmNVB69QfzoVAG",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=14820924&authToken=-zXd&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:MtK6"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/wT1jyiS-w5"
      },
      "firstName": "Mónica",
      "headline": "Ingeniera en Organización Industrial",
      "id": "wT1jyiS-w5",
      "industry": "Professional Training & Coaching",
      "lastName": "Iglesias Sanzo",
      "location": {
        "country": {"code": "es"},
        "name": "Valladolid Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_Tw3KHV7NPo_Xb9Wof2__HMaZxd5qL9HoiEzfHMpe87lXVt0EDSAyQJeQlXLIQAd68eimFOGZB7dH",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=52719368&authToken=MtK6&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:V1rB"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/v9tf6MiQg8"
      },
      "firstName": "Silvie",
      "headline": "senior architect at DOMY cz",
      "id": "v9tf6MiQg8",
      "industry": "Architecture & Planning",
      "lastName": "Juríková",
      "location": {
        "country": {"code": "cz"},
        "name": "Prague, The Capital, Czech Republic"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_73E_PMM891zE4Q-0fTMKPRojqvCwZL_0D_YpPRogIKnUbTNxiFs7jUYPVRGNRGC1mGegyyNx06pC",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=78043747&authToken=V1rB&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:cQ-C"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/1rucCPs34D"
      },
      "firstName": "Dounia",
      "headline": "IT Area Operations Supervisor at ExxonMobil",
      "id": "1rucCPs34D",
      "industry": "Oil & Energy",
      "lastName": "Keda",
      "location": {
        "country": {"code": "td"},
        "name": "Chad"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_r7IZBBmWHbvKeuEptSVFBqf5eC_jEERp-I0FBzDZBXBDva2yyWRNJvMnQ7iGdSVrAudQZtPParrZ",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=4645041&authToken=cQ-C&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:VC5e"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/Si7T17Tcxp"
      },
      "firstName": "Elmar",
      "headline": "--",
      "id": "Si7T17Tcxp",
      "lastName": "Klameth",
      "location": {
        "country": {"code": "de"},
        "name": "Germany"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=153141984&authToken=VC5e&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:lGOP"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/nMwLZHhCNT"
      },
      "firstName": "Laurent",
      "headline": "Team leader at SecuTix SA",
      "id": "nMwLZHhCNT",
      "industry": "Information Technology and Services",
      "lastName": "Kloetzer",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_JhxB8kVpoZ0h9HQZRGZH8FeOEUD29aQZRkwH8Fd_vjJpdEl4v67UabgSknS1vdbN4igW78RiTjRl",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=88079797&authToken=lGOP&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:GyQa"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/4eOt04Kr1X"
      },
      "firstName": "Elie",
      "headline": "Maintenance Team Manager for the Crédit Du Nord Web Site",
      "id": "4eOt04Kr1X",
      "industry": "Banking",
      "lastName": "KORKMAZ",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=101164444&authToken=GyQa&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "firstName": "private",
      "id": "private",
      "lastName": "private"
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:bQ8W"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/-GgKri6bzU"
      },
      "firstName": "Alberto",
      "headline": "Analista Funcional en Coremain",
      "id": "-GgKri6bzU",
      "industry": "Information Technology and Services",
      "lastName": "Laguarta Calvo",
      "location": {
        "country": {"code": "es"},
        "name": "Madrid Area, Spain"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=88488412&authToken=bQ8W&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:72_m"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/OIf8hYg4gD"
      },
      "firstName": "Vincent",
      "headline": "Head of Architecture at SecuTix SA",
      "id": "OIf8hYg4gD",
      "industry": "Information Technology and Services",
      "lastName": "Larchet",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=129799112&authToken=72_m&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:6mAx"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/l6LhE6ptqr"
      },
      "firstName": "César",
      "headline": "Aftersales Supervisor (General Motors)",
      "id": "l6LhE6ptqr",
      "industry": "Automotive",
      "lastName": "Larraga García",
      "location": {
        "country": {"code": "es"},
        "name": "Madrid Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_5KCgs73yxCDwPrICFAPGsDG10GRUl9sCF-nGsD8uhFfwmtSGdntlqSqCAuUk-AUmkP8_vE-vN-XV",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=114628748&authToken=6mAx&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:XSk5"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/zGdqsV379I"
      },
      "firstName": "jeff",
      "headline": "ENSEIGNANT EN MARKETING ET COMMERCE INTERNATIONAL chez Université de Nantes",
      "id": "zGdqsV379I",
      "industry": "Higher Education",
      "lastName": "LE BERRE",
      "location": {
        "country": {"code": "fr"},
        "name": "Nantes Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=68568004&authToken=XSk5&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:-1JB"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/YtOdsgvXzi"
      },
      "firstName": "Xavier",
      "headline": "Directeur Commercial at SFEIR",
      "id": "YtOdsgvXzi",
      "industry": "Information Technology and Services",
      "lastName": "Lefevre",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_Vu55r8H5_tdGbRCP4INnrGIFTqxSwZiPZwlVriEVYAwgNpq1nEvWY_Oq7s0tHxGxR7bU0kNUh-09",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=2717526&authToken=-1JB&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:vlSj"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/IRBlLaM5GK"
      },
      "firstName": "Jean-Gabriel",
      "headline": "Chef de projet informatique",
      "id": "IRBlLaM5GK",
      "industry": "Computer Software",
      "lastName": "LIMBOURG",
      "location": {
        "country": {"code": "fr"},
        "name": "Rennes Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_H1YHLangkHJ5gfgNQc2BLScKk7jelupNE-EBLSF8UEdZmHIqkzmXwDPmE3gB-wjZdry9oozkuk1k",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=15718944&authToken=vlSj&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:2Zqf"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/yy8_DoaV1o"
      },
      "firstName": "Pedro",
      "headline": "Business Development Director",
      "id": "yy8_DoaV1o",
      "industry": "Information Technology and Services",
      "lastName": "Lindsey Eguiguren",
      "location": {
        "country": {"code": "es"},
        "name": "Madrid Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_D5pUSVeGHJ1o9HFoSifHSMZ0Ej5Lnanof3HXSMZGvUlzWETET_2BiJ22krLVzdB62QO5COEDHw_s",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=29321507&authToken=2Zqf&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:UdVJ"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/b4Pg6Pmh9r"
      },
      "firstName": "Jérôme",
      "headline": "Entrepreneur and web APIs expert",
      "id": "b4Pg6Pmh9r",
      "industry": "Computer Software",
      "lastName": "Louvel",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_6X3m4fDpKk7BzKg8k_TA4uupt84czng8kizA4ugmS52Fo-EhEGACn2d8yaZHnlyuFFiKcIifYCWn",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=6350866&authToken=UdVJ&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:FjKA"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/EepTdYkUad"
      },
      "firstName": "Céline",
      "headline": "Developpeuse Java chez SFEIR",
      "id": "EepTdYkUad",
      "industry": "Computer Software",
      "lastName": "Louvet",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_itf3qR5jBhqMK_gpTzS-qZC1zQis-5SpTvRYqZG2o_cED3sy7N0Ds4nGRH_5lCurGlD0R0y9C944",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=61669153&authToken=FjKA&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:e0fN"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/KGgbglBM6e"
      },
      "firstName": "Teresa",
      "headline": "Communications Specialist at Boeing Commercial Airplanes",
      "id": "KGgbglBM6e",
      "industry": "Aviation & Aerospace",
      "lastName": "Loy",
      "location": {
        "country": {"code": "us"},
        "name": "Greater Seattle Area"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_dtjoY7cYFvYEKWG3WlxJY2br51REt2P3wrdvYDX3s9fsSekT5ND6rSKfwgUvAItDHl0qlE9KSGaK",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=47330414&authToken=e0fN&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:TcRX"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/xXOitgbY89"
      },
      "firstName": "Pierre",
      "headline": "Agile Developer and Facilitator",
      "id": "xXOitgbY89",
      "industry": "Information Technology and Services",
      "lastName": "Mage",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_QQ0tAdjKZzVRU8FjX3aTAwfpZNS9BiFjXXIDAE0hb-M6ebrgo8fYxowanMDdqX6Ab5juODTnHQ0T",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=27358211&authToken=TcRX&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:UTNM"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/LMXEdlkOv9"
      },
      "firstName": "Amandine",
      "headline": "Chef de projet AMOA - Product Owner",
      "id": "LMXEdlkOv9",
      "industry": "Internet",
      "lastName": "Marousez",
      "location": {
        "country": {"code": "fr"},
        "name": "Lille Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_pCD07nJ1VOCfnLvWOLxh7zWK4x5DNQqWO6J87zwDFJljwGidKkj13v0i9tL-cTz5jTf38tvZbga_",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=8816048&authToken=UTNM&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:va3v"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/NMc9Ug_p_x"
      },
      "firstName": "Sara",
      "headline": "Junior engineer",
      "id": "NMc9Ug_p_x",
      "industry": "Renewables & Environment",
      "lastName": "Martín de la Fuente",
      "location": {
        "country": {"code": "be"},
        "name": "Brussels Area, Belgium"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_JqFQXFvY7WJr0MaVUABMXk9G_df80VaVRK1JXkqYy7RKhYJsv-cEEX8KSX7xyjf94cLZIiQXQdsR",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=51118657&authToken=va3v&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:UtaW"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/CMz74L5TZI"
      },
      "firstName": "Emmanuel",
      "headline": "Senior Manager chez ELCA infomatique SA",
      "id": "CMz74L5TZI",
      "industry": "Information Technology and Services",
      "lastName": "Mayer",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_dCvykoMhyIRQ4q4UbLnfkesfpuuE4-4UwhTikeHriWZsFnfR5k5KoHyO-G2vUzJBHTBCwfs45fxN",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=88250566&authToken=UtaW&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:aMo7"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/IIhtOGN_hw"
      },
      "firstName": "Sébastien",
      "headline": "Ingénieur at Eurogiciel",
      "id": "IIhtOGN_hw",
      "industry": "Computer Software",
      "lastName": "Mazzon",
      "location": {
        "country": {"code": "fr"},
        "name": "Nantes Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_zVjRUCK4IQgO87R8BsaHU3Nzd3Zr82R8BRdkU31k9Xuhxe2hMpDczTbEF74fiIVuqJ0LNQD2gVcH",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=12834734&authToken=aMo7&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:VEY3"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/3AG_nPpolQ"
      },
      "firstName": "Ferghal",
      "headline": "English Language Teacher at Leicester Square School of English",
      "id": "3AG_nPpolQ",
      "industry": "Higher Education",
      "lastName": "McTaggart",
      "location": {
        "country": {"code": "gb"},
        "name": "London, United Kingdom"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_5fq3TIaktOjUwBKVLWnYTegeKRfRwl3VFHhYTeDn7ORINz9sddXDfHMZYv7XHnh9k290mfPkanaT",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=141391690&authToken=VEY3&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "firstName": "private",
      "id": "private",
      "lastName": "private"
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:FJZs"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/cY3oOBCwg-"
      },
      "firstName": "Zdenek",
      "headline": "Researcher at Czech Technical University in Prague",
      "id": "cY3oOBCwg-",
      "industry": "Research",
      "lastName": "Mikovec",
      "location": {
        "country": {"code": "cz"},
        "name": "Czech Republic"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_SrapvYbAsQhXrXW17qjfvjL0V_-HrbM1fAV_vpQfLQ64fimP39OrRy-3qDtcP8Z0u12GsUNjzOZV",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=5290557&authToken=FJZs&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:_DAy"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/wyryrhk2ly"
      },
      "firstName": "Jean-Baptiste",
      "headline": "Chief Product Owner at Adeo Services",
      "id": "wyryrhk2ly",
      "industry": "Information Technology and Services",
      "lastName": "Monville",
      "location": {
        "country": {"code": "fr"},
        "name": "Lille Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=57512977&authToken=_DAy&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:tBn9"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/Gt43zgHqii"
      },
      "firstName": "Sebastien",
      "headline": "IT Consultant at Generali",
      "id": "Gt43zgHqii",
      "industry": "Information Services",
      "lastName": "Morhan",
      "location": {
        "country": {"code": "fr"},
        "name": "France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_mVxsC0kzoMcvhHXcuUDLCgkJoyA5haqc2jwLCgz5zMLv0EiBGp7n2jTwX-ls_dzR7JgkSVFkKpR0",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=15559875&authToken=tBn9&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:Nly3"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/WvWaeyYt2b"
      },
      "firstName": "Audrey",
      "headline": "Ingénieur d'études et de développement Java / Java EE chez SFEIR",
      "id": "WvWaeyYt2b",
      "industry": "Computer Software",
      "lastName": "Neveu",
      "location": {
        "country": {"code": "fr"},
        "name": "France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_Z3_74XHpP3jx4Nxu4G3K4bUglie7qNxuV6Br4bW2m6yxIre2qFP_nFjGxSHKBPY8MGTlchUY_Q5f",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=64519180&authToken=Nly3&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:LeA3"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/MIRGM3nEhs"
      },
      "firstName": "Emma",
      "headline": "JAVA Consultant",
      "id": "MIRGM3nEhs",
      "industry": "Information Technology and Services",
      "lastName": "Nieto",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=67033557&authToken=LeA3&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:FZtT"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/dVtGciMYZh"
      },
      "firstName": "Lucía",
      "headline": "Editora Avid y Final Cut",
      "id": "dVtGciMYZh",
      "industry": "Broadcast Media",
      "lastName": "Nieto Tejada",
      "location": {
        "country": {"code": "es"},
        "name": "Madrid Area, Spain"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_QoM0AdmzUqWU63NjQueuAEmvUAS9oCNjXeS8AE0vkqM6z6_go2W1xowUvxDdW5vAbdU3ODT4AMSc",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=95882290&authToken=FZtT&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:4Kee"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/Wud9ahWMGO"
      },
      "firstName": "Erell",
      "headline": "Clinical research assitant",
      "id": "Wud9ahWMGO",
      "industry": "Pharmaceuticals",
      "lastName": "OLIVO",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=99889289&authToken=4Kee&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:aUS8"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/p_Urd3wxq5"
      },
      "firstName": "Aurélien",
      "headline": "CTO at SFEIR",
      "id": "p_Urd3wxq5",
      "industry": "Information Technology and Services",
      "lastName": "Pelletier",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_ePvupfvS-PjHr-tio-10pSN_rlZdp-tiotT1pSkPfNuJin5_Xv58tDrgO049xzPfWKBt1onGdmRb",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=2005626&authToken=aUS8&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:BdrL"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/yN6XYFWK-q"
      },
      "firstName": "Marcos",
      "headline": "Manager at ELCA",
      "id": "yN6XYFWK-q",
      "industry": "Information Technology and Services",
      "lastName": "Perez, PMP",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_NArAmTtxpxfxr-bbqlACmGktyssxt-bb9PXmmG_T_xm7SnKF4chjhCc7tqV8AzQwv--fT5AF5NOL",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=1607731&authToken=BdrL&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "firstName": "private",
      "id": "private",
      "lastName": "private"
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:b8b6"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/SyREETiNG8"
      },
      "firstName": "antje",
      "headline": "--",
      "id": "SyREETiNG8",
      "lastName": "peter",
      "location": {
        "country": {"code": "de"},
        "name": "Germany"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=154122944&authToken=b8b6&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:iya4"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/MPPuRDzn3T"
      },
      "firstName": "Iya",
      "headline": "International & GR Relations Manager в 2018 FIFA World Cup Russia Local Organising Committee",
      "id": "MPPuRDzn3T",
      "industry": "International Affairs",
      "lastName": "Bordyuzhenko",
      "location": {
        "country": {"code": "ru"},
        "name": "Russian Federation"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_uQ-VaJdjuR_oUgXE2FzdasYy2yX6BgvEab65asJ81Vr9eJCoh8G98VSmGlkJqsNQS5rX3jFJR-LI",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=153656842&authToken=iya4&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:-tuX"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/jqLyMqcrcd"
      },
      "firstName": "Quang-Khai",
      "headline": "Expert conseil - opérations de fonds",
      "id": "jqLyMqcrcd",
      "industry": "Computer Software",
      "lastName": "Pham",
      "location": {
        "country": {"code": "ca"},
        "name": "Montreal, Canada Area"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_R7Vm6_fWBeHioFDzRwJg6hEWcSOfECjzMeaA6heqHeo0v6ovBWoCe8pVJiYrd5pJVu4KdbsK_fQj",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=81161476&authToken=-tuX&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:GFbw"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/aP-PsNdmga"
      },
      "firstName": "Christophe",
      "headline": "computing engineer at SFEIR",
      "id": "aP-PsNdmga",
      "industry": "Internet",
      "lastName": "PHU",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=42439272&authToken=GFbw&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:SkhA"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/md9yL66Gm-"
      },
      "firstName": "Charlotte",
      "headline": "Sales and Business Development Manager",
      "id": "md9yL66Gm-",
      "industry": "Machinery",
      "lastName": "PINEAU",
      "location": {
        "country": {"code": "au"},
        "name": "Brisbane Area, Australia"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_ncgMlb_jWld1tDzp9KmQlX_jwA7rtDzpqvW6lLP3NBUhSWGyVASv05Ff5pffAoqrBqxbYCDhFK7P",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=15970831&authToken=SkhA&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:rJ0Q"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/lYTXU1ECbQ"
      },
      "firstName": "Nicolas",
      "headline": "Software engineer",
      "id": "lYTXU1ECbQ",
      "industry": "Computer Software",
      "lastName": "Rémond",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_eMnW9EzLCCyvmJmx6gB99db6GGfLu00xoYC99HNqjFRzPUH0XxQ5ZehV2u7Vf4OPWsNBJ76dxvih",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=4712798&authToken=rJ0Q&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:EJEI"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/oQG_A737Nv"
      },
      "firstName": "Daniel",
      "headline": "Software Engineer en ELCA infomatique SA",
      "id": "oQG_A737Nv",
      "industry": "Information Technology and Services",
      "lastName": "Rodríguez Postigo",
      "location": {
        "country": {"code": "ch"},
        "name": "Zürich Area, Switzerland"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=51940077&authToken=EJEI&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:qUI0"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/T8atcZS3mf"
      },
      "firstName": "Nadia",
      "headline": "Project manager, Product owner",
      "id": "T8atcZS3mf",
      "industry": "Internet",
      "lastName": "Sol",
      "location": {
        "country": {"code": "fr"},
        "name": "Lille Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_R7Vm6_eNzeJDECuzvwZA6howcSOfECjzMmaA6heqHeo0v6ovBWoCe8pVJiYrd5pJVu4KdbnOV61j",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=24161235&authToken=qUI0&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:F1fU"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/hrbDP99t9L"
      },
      "firstName": "Fabrice",
      "headline": "Senior Web Java - Java EE Developer at BNP Paribas Arbitrage",
      "id": "hrbDP99t9L",
      "industry": "Computer Software",
      "lastName": "Sznajderman",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_mB1VmUzp_pqFPpvw2vr5mJq070Q5Pxvw2KF5m4v_KVtvuRCIGli9hZ3STl6srZNb7NAXTxF7je5O",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=6019663&authToken=F1fU&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:q9FK"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/3t67NpnrqQ"
      },
      "firstName": "Slim",
      "headline": "Team Leader / Référent Hadoop chez Powerspace",
      "id": "3t67NpnrqQ",
      "industry": "Computer Software",
      "lastName": "Tebourbi",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_uBxCSyNlBg1X1kA6GAyAS05xcRv6PC36aKwjS0v_HOT9u69Qhl7mix3SJvzJr5hoSNgyCZwF5Y8g",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=26224191&authToken=q9FK&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:_pwe"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/wCHc5UAmWV"
      },
      "firstName": "Maxime",
      "headline": "Senior Java Developper & Interoperability Specialist",
      "id": "wCHc5UAmWV",
      "industry": "Information Technology and Services",
      "lastName": "Terrettaz",
      "location": {
        "country": {"code": "ca"},
        "name": "Montreal, Canada Area"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_Lw2NlIpzIlIs5eQpbmfFlegvIcf9LeFpbu4IlexHq-R6V2ryWSpZ0HI6LM7dQ76rXeaEYfDxAdvH",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=15039336&authToken=_pwe&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:Vobq"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/jEWQGS1Oig"
      },
      "firstName": "Axel",
      "headline": "Engineering Manager at Qualys",
      "id": "jEWQGS1Oig",
      "industry": "Computer & Network Security",
      "lastName": "Tessier",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_Oz05kBl0QWc-PuxUOA7VkqAlXE8Clf7UplIVkqchR2NtmwZRt1fWoN_aobhg-HmB09jUwPWydj-U",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=1082534&authToken=Vobq&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:VGMF"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/CH_lS-l_z_"
      },
      "firstName": "Sovanneary",
      "headline": "Chef de Projet Stockage chez SFR",
      "id": "CH_lS-l_z_",
      "industry": "Information Services",
      "lastName": "Than",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=37381294&authToken=VGMF&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:Lt5i"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/gipSG31Nie"
      },
      "firstName": "Thierry",
      "headline": "Project Director - SFEIR Groupe",
      "id": "gipSG31Nie",
      "industry": "Information Technology and Services",
      "lastName": "TREPIED",
      "location": {
        "country": {"code": "fr"},
        "name": "France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_WTL7mWwitVp5J1-II5qpmISm-0IwMzPIIQArmIoxDV0UQlkwLbz_hwYlplwNV-tFeCFlTuqrQmYi",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=43418674&authToken=Lt5i&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:6ZJl"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/gD1LJIn2mT"
      },
      "firstName": "Sébastien",
      "headline": "Senior Software Engineer chez SecuTix SA",
      "id": "gD1LJIn2mT",
      "industry": "Information Technology and Services",
      "lastName": "Treyvaud",
      "location": {
        "country": {"code": "ch"},
        "name": "Geneva Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_uQ-Va4V-mpBwBOqEu_i5asRp20X6BgvEaX65asJ81Vr9eJCoh8G98VSmGlkJqsNQS5rX3jbajgCI",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=31724189&authToken=6ZJl&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:t0y5"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/th-0sJ0b3-"
      },
      "firstName": "Francky",
      "headline": "Head at IREALITE",
      "id": "th-0sJ0b3-",
      "industry": "Computer Software",
      "lastName": "Trichet",
      "location": {
        "country": {"code": "fr"},
        "name": "Nantes Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_Pk_hAc0Ot1ky9-1gAXCPANUArN8AqqigA_BxANg2flN3IKqj0CP2xqdGOVhaB1GltbTOO1ivcnaB",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=24022256&authToken=t0y5&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:X0lm"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/N5F2CefPCg"
      },
      "firstName": "Rea",
      "headline": "Freelancer presso AARCH-MI",
      "id": "N5F2CefPCg",
      "industry": "Architecture & Planning",
      "lastName": "Turohan",
      "location": {
        "country": {"code": "it"},
        "name": "Brescia Area, Italy"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_io443YZHFyQcEmcc3fdw3judIjA9oDncTI2b3j0zqUL6zWTB72Hq7gwRLrldWoBRGdV6asDxqrc1",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=161200606&authToken=X0lm&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:veT0"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/-VEyNXIAJf"
      },
      "firstName": "Joseba",
      "headline": "Head of Division ECM at ELCA infomatique SA",
      "id": "-VEyNXIAJf",
      "industry": "Information Technology and Services",
      "lastName": "Urzelai",
      "location": {
        "country": {"code": "ch"},
        "name": "Zürich Area, Switzerland"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_RNyrIkFxMdOCtheIMBx7I66KMIwit_dIJre7I6qTQSx1SFOwBtup5Q87B6IpAkHFVBYaXTQ-oP7-",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=1714194&authToken=veT0&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:M-Tp"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/UChc6Pp2IN"
      },
      "firstName": "Orchidée",
      "headline": "Gestionnaire Marketing Internet chez Videotron",
      "id": "UChc6Pp2IN",
      "industry": "Information Technology and Services",
      "lastName": "Vaussard",
      "location": {
        "country": {"code": "ca"},
        "name": "Montreal, Canada Area"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_sMe3tGkWBrR_ahlAqOoYt8LLzzg826TA4RyYt8NJoPWK1Cnl9xZDphhcR4jx738jUsE0gFQuNken",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=5843867&authToken=M-Tp&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:wo2_"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/V4jfuAGXDh"
      },
      "firstName": "Francois",
      "headline": "Developer at Sfeir",
      "id": "V4jfuAGXDh",
      "industry": "Computer Software",
      "lastName": "Wauquier",
      "location": {
        "country": {"code": "fr"},
        "name": "Paris Area, France"
      },
      "pictureUrl": "http://m3.licdn.com/mpr/mprx/0_gyqUotveuIKTTgsLgYQHoAnouaqaSgsL0ghXorQnPIhOtJS5AsXBkK-ZC8NAasUdyx95LBNXCap7",
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=34465418&authToken=wo2_&authType=name&trk=api*a196273*s204372*"}
    },
    {
      "apiStandardProfileRequest": {
        "headers": {
          "_total": 1,
          "values": [{
            "name": "x-li-auth-token",
            "value": "name:haZR"
          }]
        },
        "url": "http://api.linkedin.com/v1/people/3PD7tIJqws"
      },
      "firstName": "Sayaka",
      "headline": "Wimbledon 学生",
      "id": "3PD7tIJqws",
      "lastName": "Yamaichi",
      "location": {
        "country": {"code": "gb"},
        "name": "London, United Kingdom"
      },
      "siteStandardProfileRequest": {"url": "http://www.linkedin.com/profile?viewProfile=&key=184910677&authToken=haZR&authType=name&trk=api*a196273*s204372*"}
    }
  ]
}

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<person>
  <first-name>Pascal</first-name>
  <last-name>Guilcher</last-name>
  <headline>Senior Web Java J2EE Engineer Developer at SFEIR</headline>
</person>

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<connections total="83">
  <person>
    <id>tU7PW4TMdZ</id>
    <first-name>David</first-name>
    <last-name>Aboulkheir</last-name>
    <headline>Développeur Architecte Artisan J2EE, Agiliste, Manager</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_N-oHBbXGL8Z-1Iyp99sBBXA2FCf00eyp91OBBXGO4bRfh2wy4qVXJknrd27hy7grvAH9Z_rV9lGy</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/tU7PW4TMdZ</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:AtQo</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=104566555&amp;authToken=AtQo&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>19p4HR3pMy</id>
    <first-name>Yasmine</first-name>
    <last-name>Aite</last-name>
    <headline>Ingénieur Études et Développement JAVA/J2EE GWT chez SFEIR</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_pCD07netVs8C9QXWyT2u7z4g4p5DNQqWY5J87zwDFJljwGidKkj13v0i9tL-cTz5jTf38tz2-dM_</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/19p4HR3pMy</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:DYdd</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=35877499&amp;authToken=DYdd&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>vauS5lrlN3</id>
    <first-name>Ainhoa</first-name>
    <last-name>Apesteguía</last-name>
    <headline>Gestor de Proyectos en Batz S. Coop.</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_XGm9LdaPIadqcoWZXkSLLwYgWWDZvoWZQLsWLwSfnuJeEmY4eXYVwIJ3bFSb9DeNL3uHo2PwVc_C</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/vauS5lrlN3</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:8X2m</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=29328722&amp;authToken=8X2m&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Bilbao Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Mechanical or Industrial Engineering</industry>
  </person>
  <person>
    <id>uMAPCr_mlY</id>
    <first-name>Sylvie</first-name>
    <last-name>Belze</last-name>
    <headline>Responsable des Ressources Humaines chez SFEIR</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/uMAPCr_mlY</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:i5u9</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=77301287&amp;authToken=i5u9&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>wsOzxjfAYM</id>
    <first-name>Reynald</first-name>
    <last-name>Borer</last-name>
    <headline>Software Architect at SecuTix SA</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_y_g-snaKJ6G3nFIa-XY3szZPRX6un3EaO3WSszeCX3-yW5gmr5SOqvpuzEQPz6wGg8x2vt9_q6kc</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/wsOzxjfAYM</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:jIlU</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=15084104&amp;authToken=jIlU&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>WVP61eMKYX</id>
    <first-name>Celine</first-name>
    <last-name>Briand</last-name>
    <headline>SEUR Professional Services Operation Representative at McAfee</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_IP83MDL-l8xLKvS_5q--Mmc-15MdKvS_dcNYM7ka2iSJ7AsibvKDcfrhjeJ91tu7oKC0nH9bPrEI</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/WVP61eMKYX</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:OJBq</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=108133630&amp;authToken=OJBq&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Retail</industry>
  </person>
  <person>
    <id>LgHJbYSoen</id>
    <first-name>Amelia</first-name>
    <last-name>Carrera</last-name>
    <headline>senior government bonds broker at Kepler</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/LgHJbYSoen</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:0W4l</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=10754119&amp;authToken=0W4l&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Financial Services</industry>
  </person>
  <person>
    <id>nFbHKDmpJ5</id>
    <first-name>Jesus</first-name>
    <last-name>Cobo</last-name>
    <headline>IT manager</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_FiVC9muAv5HNc3j156Jj92jAcbOJRCD15Xaj92mlHCodX6VPwLomZuVxJdY6Z5206h4yJwrAD43P</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/nFbHKDmpJ5</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:saYv</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=885524&amp;authToken=saYv&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Madrid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>QkEDgN5NOU</id>
    <first-name>Annabella</first-name>
    <last-name>Da Encarnacao</last-name>
    <headline>Marketing Director at Team Europe</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_Rm-zuCWnfjx_kYzkRE1ouhWUSUUfXjkkM76EuhHQtY70RMPXBeGJG8ye_zRrbV5eVSrI_b9eVXcd</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/QkEDgN5NOU</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:4NCL</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=5381182&amp;authToken=4NCL&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Berlin Area, Germany</name>
      <country>
        <code>de</code>
      </country>
    </location>
    <industry>Marketing and Advertising</industry>
  </person>
  <person>
    <id>6IJLo2rCHv</id>
    <first-name>Pierre</first-name>
    <last-name>Dalmaz</last-name>
    <headline>Administrateur à Panhard General Defence</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/6IJLo2rCHv</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:yCcV</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=37149582&amp;authToken=yCcV&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Services</industry>
  </person>
  <person>
    <id>7yGmN8jh6I</id>
    <first-name>Alexis</first-name>
    <last-name>Davoux</last-name>
    <headline>Information security architect</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/7yGmN8jh6I</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:m8rO</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=12281201&amp;authToken=m8rO&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>4pr7XBPlxl</id>
    <first-name>Salvador</first-name>
    <last-name>Diaz</last-name>
    <headline>Software Engineer</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/4pr7XBPlxl</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:S88C</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=127861262&amp;authToken=S88C&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>EFIZSDk9iQ</id>
    <first-name>Michel</first-name>
    <last-name>Dommen</last-name>
    <headline>Senior Software Engineer at Sysmosoft SA</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/EFIZSDk9iQ</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:sTvE</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=12608013&amp;authToken=sTvE&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>fsWhv0kEbQ</id>
    <first-name>Emmanuel</first-name>
    <last-name>Duchasténier</last-name>
    <headline>R&amp;D Engineer at BonitaSoft</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_u_fFGJm1LVlVqohs2XHZGsajLsGE9WTsaCRZGse7Vx9sdDnVh50IuVpTIqCvvm8nS8DJDjs5qM9M</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/fsWhv0kEbQ</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:SdgE</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=6682260&amp;authToken=SdgE&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Grenoble Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Services</industry>
  </person>
  <person>
    <id>private</id>
    <first-name>private</first-name>
    <last-name>private</last-name>
  </person>
  <person>
    <id>YmOlqP87H9</id>
    <first-name>Alain</first-name>
    <last-name>Escaffre</last-name>
    <headline>Presales and Product Manager at Nuxeo</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_CHQ4YJ7Xavivw0bD2DbFYU7XmKFzdJ9DhftbYUO4l91L9g3SaanqrRE98gboEOc3_Ik6lp2uI7C_</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/YmOlqP87H9</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:GZHK</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=6033923&amp;authToken=GZHK&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>Kgwjn_jl5i</id>
    <first-name>Guillermo</first-name>
    <last-name>Estévez</last-name>
    <headline>Dynamics CRM Consultant at Avanade</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_0hOv4PJOb6Q2qeH8jXY64AIPwLN29DW8g3oo4AdiNh8pdWYhP6aMnlgD5wq1voeuOipwcncXc77W</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/Kgwjn_jl5i</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:f01x</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=16090091&amp;authToken=f01x&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Madrid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>qcl0HWta7l</id>
    <first-name>James</first-name>
    <last-name>Fairbairn</last-name>
    <headline>Customer development executive at Nestle</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_jOgiHAN9zoG_ikO60VypHPiBBEvfi3a6x0WyHPXke7T0p5JQl4SfQ1KEMXzr86fopjxjFqn_72y-</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/qcl0HWta7l</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:1Dt8</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=111880735&amp;authToken=1Dt8&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>London, United Kingdom</name>
      <country>
        <code>gb</code>
      </country>
    </location>
    <industry>Food Production</industry>
  </person>
  <person>
    <id>nHR7g8Awhp</id>
    <first-name>Geoffrey</first-name>
    <last-name>Garnotel</last-name>
    <headline>Team Leader / Manager chez SFEIR</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/nHR7g8Awhp</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:b_8j</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=141157403&amp;authToken=b_8j&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>8IqqkNuOUp</id>
    <first-name>Didier</first-name>
    <last-name>Girard</last-name>
    <headline>COO and CTO SFEIR</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_M5umgFytNlYDsLK2JXaggkRYNAd3nLA2RkZAgkRawqjAWT6uz_yC1X7hsxWOzGrhZQmKtiWeBE0J</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/8IqqkNuOUp</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:2-tZ</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=158449&amp;authToken=2-tZ&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>2BKDHFoOZD</id>
    <first-name>Pierre</first-name>
    <last-name>Gosselin</last-name>
    <headline>Tech leader at SFEIR</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/2BKDHFoOZD</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:PV_w</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=7616067&amp;authToken=PV_w&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>Z7i1Vp-MIf</id>
    <first-name>Jessica</first-name>
    <last-name>Gross</last-name>
    <headline>Student Aide at Shaker Heights City School District</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_5KCgs7Aj05xNlrsCFchasDFp0TRUl9sCFlnGsD8uhFfwmtSGdntlqSqCAuUk-AUmkP8_vEO-n49V</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/Z7i1Vp-MIf</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:1yzG</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=148128918&amp;authToken=1yzG&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Cleveland/Akron, Ohio Area</name>
      <country>
        <code>us</code>
      </country>
    </location>
    <industry>Education Management</industry>
  </person>
  <person>
    <id>yIVfmsmAgP</id>
    <first-name>Samir</first-name>
    <last-name>Guesmia</last-name>
    <headline>Sr Project Manager &amp; Sr Software Architect</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_bNwbMo9j68snOw27LqeNMWrDQ5WNYw27LPx4MWljM8gkTfRfItUwcdL1eIdwjuD_QBWMnafy_IW5</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/yIVfmsmAgP</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:KSfA</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=7904549&amp;authToken=KSfA&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>0Qa1Px-4i_</id>
    <first-name>Michael</first-name>
    <last-name>Hammond</last-name>
    <headline>Vice President at PNC Bank</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_n2b0MbSQ0bxlotM7NHQhMXydg_WpEc47NeP8MLfZCLg2vPffVoB1c5UnPmdidrJ_Bf53nCgY22TG</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/0Qa1Px-4i_</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:uTVC</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=1724541&amp;authToken=uTVC&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Cleveland/Akron, Ohio Area</name>
      <country>
        <code>us</code>
      </country>
    </location>
    <industry>Financial Services</industry>
  </person>
  <person>
    <id>UTX_BrKRC_</id>
    <first-name>Eduardo</first-name>
    <last-name>Haro</last-name>
    <headline>Co-Founder at Nazaríes IT</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_9fXpRT2epLxAwBZTnmQfRiDbpCVjwBJTNSr_RiDsibaDN1a3sdqrv_MN-2sGHK4Sc26Gqk0EHvBe</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/UTX_BrKRC_</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:cbLx</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=85095667&amp;authToken=cbLx&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Madrid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>hmTvXDE4Zj</id>
    <first-name>Yifeng</first-name>
    <last-name>HE</last-name>
    <headline>IT Engineer</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_uBxCSytlzJiH1F36a9jjS0cgcRv6PC36azwjS0v_HOT9u69Qhl7mix3SJvzJr5hoSNgyCZFYRpzg</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/hmTvXDE4Zj</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:FFnW</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=31342388&amp;authToken=FFnW&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>Y-RcljNp4l</id>
    <first-name>Olivier</first-name>
    <last-name>Hedin</last-name>
    <headline>Software architect at ADEO Services</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_ncgMlQK0IB4K-Dkpqzu6lXrAwt7rtDzpqvW6lLP3NBUhSWGyVASv05Ff5pffAoqrBqxbYCT5T5VP</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/Y-RcljNp4l</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:ODOg</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=25803725&amp;authToken=ODOg&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Lille Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>jODYH-rgmB</id>
    <first-name>Manuel</first-name>
    <last-name>Hitz</last-name>
    <headline>Front-End Engineer at ELCA Informatique SA</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_vJv4J_CE3TYgfjwCzVcbJ3K6D_RASYVCc4TbJ3A9-Qf3tVDGJ05qBT54iDUaaMRmNVB69QfzoVAG</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/jODYH-rgmB</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:-zXd</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=14820924&amp;authToken=-zXd&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>wT1jyiS-w5</id>
    <first-name>Mónica</first-name>
    <last-name>Iglesias Sanzo</last-name>
    <headline>Ingeniera en Organización Industrial</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_Tw3KHV7NPo_Xb9Wof2__HMaZxd5qL9HoiEzfHMpe87lXVt0EDSAyQJeQlXLIQAd68eimFOGZB7dH</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/wT1jyiS-w5</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:MtK6</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=52719368&amp;authToken=MtK6&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Valladolid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Professional Training &amp; Coaching</industry>
  </person>
  <person>
    <id>v9tf6MiQg8</id>
    <first-name>Silvie</first-name>
    <last-name>Juríková</last-name>
    <headline>senior architect at DOMY cz</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_73E_PMM891zE4Q-0fTMKPRojqvCwZL_0D_YpPRogIKnUbTNxiFs7jUYPVRGNRGC1mGegyyNx06pC</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/v9tf6MiQg8</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:V1rB</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=78043747&amp;authToken=V1rB&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Prague, The Capital, Czech Republic</name>
      <country>
        <code>cz</code>
      </country>
    </location>
    <industry>Architecture &amp; Planning</industry>
  </person>
  <person>
    <id>1rucCPs34D</id>
    <first-name>Dounia</first-name>
    <last-name>Keda</last-name>
    <headline>IT Area Operations Supervisor at ExxonMobil</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_r7IZBBmWHbvKeuEptSVFBqf5eC_jEERp-I0FBzDZBXBDva2yyWRNJvMnQ7iGdSVrAudQZtPParrZ</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/1rucCPs34D</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:cQ-C</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=4645041&amp;authToken=cQ-C&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Chad</name>
      <country>
        <code>td</code>
      </country>
    </location>
    <industry>Oil &amp; Energy</industry>
  </person>
  <person>
    <id>Si7T17Tcxp</id>
    <first-name>Elmar</first-name>
    <last-name>Klameth</last-name>
    <headline>--</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/Si7T17Tcxp</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:VC5e</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=153141984&amp;authToken=VC5e&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Germany</name>
      <country>
        <code>de</code>
      </country>
    </location>
  </person>
  <person>
    <id>nMwLZHhCNT</id>
    <first-name>Laurent</first-name>
    <last-name>Kloetzer</last-name>
    <headline>Team leader at SecuTix SA</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_JhxB8kVpoZ0h9HQZRGZH8FeOEUD29aQZRkwH8Fd_vjJpdEl4v67UabgSknS1vdbN4igW78RiTjRl</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/nMwLZHhCNT</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:lGOP</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=88079797&amp;authToken=lGOP&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>4eOt04Kr1X</id>
    <first-name>Elie</first-name>
    <last-name>KORKMAZ</last-name>
    <headline>Maintenance Team Manager for the Crédit Du Nord Web Site</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/4eOt04Kr1X</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:GyQa</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=101164444&amp;authToken=GyQa&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Banking</industry>
  </person>
  <person>
    <id>private</id>
    <first-name>private</first-name>
    <last-name>private</last-name>
  </person>
  <person>
    <id>-GgKri6bzU</id>
    <first-name>Alberto</first-name>
    <last-name>Laguarta Calvo</last-name>
    <headline>Analista Funcional en Coremain</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/-GgKri6bzU</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:bQ8W</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=88488412&amp;authToken=bQ8W&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Madrid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>OIf8hYg4gD</id>
    <first-name>Vincent</first-name>
    <last-name>Larchet</last-name>
    <headline>Head of Architecture at SecuTix SA</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/OIf8hYg4gD</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:72_m</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=129799112&amp;authToken=72_m&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>l6LhE6ptqr</id>
    <first-name>César</first-name>
    <last-name>Larraga García</last-name>
    <headline>Aftersales Supervisor (General Motors)</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_5KCgs73yxCDwPrICFAPGsDG10GRUl9sCF-nGsD8uhFfwmtSGdntlqSqCAuUk-AUmkP8_vE-vN-XV</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/l6LhE6ptqr</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:6mAx</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=114628748&amp;authToken=6mAx&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Madrid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Automotive</industry>
  </person>
  <person>
    <id>zGdqsV379I</id>
    <first-name>jeff</first-name>
    <last-name>LE BERRE</last-name>
    <headline>ENSEIGNANT EN MARKETING ET COMMERCE INTERNATIONAL chez Université de Nantes</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/zGdqsV379I</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:XSk5</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=68568004&amp;authToken=XSk5&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Nantes Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Higher Education</industry>
  </person>
  <person>
    <id>YtOdsgvXzi</id>
    <first-name>Xavier</first-name>
    <last-name>Lefevre</last-name>
    <headline>Directeur Commercial at SFEIR</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_Vu55r8H5_tdGbRCP4INnrGIFTqxSwZiPZwlVriEVYAwgNpq1nEvWY_Oq7s0tHxGxR7bU0kNUh-09</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/YtOdsgvXzi</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:-1JB</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=2717526&amp;authToken=-1JB&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>IRBlLaM5GK</id>
    <first-name>Jean-Gabriel</first-name>
    <last-name>LIMBOURG</last-name>
    <headline>Chef de projet informatique</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_H1YHLangkHJ5gfgNQc2BLScKk7jelupNE-EBLSF8UEdZmHIqkzmXwDPmE3gB-wjZdry9oozkuk1k</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/IRBlLaM5GK</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:vlSj</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=15718944&amp;authToken=vlSj&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Rennes Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>yy8_DoaV1o</id>
    <first-name>Pedro</first-name>
    <last-name>Lindsey Eguiguren</last-name>
    <headline>Business Development Director</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_D5pUSVeGHJ1o9HFoSifHSMZ0Ej5Lnanof3HXSMZGvUlzWETET_2BiJ22krLVzdB62QO5COEDHw_s</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/yy8_DoaV1o</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:2Zqf</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=29321507&amp;authToken=2Zqf&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Madrid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>b4Pg6Pmh9r</id>
    <first-name>Jérôme</first-name>
    <last-name>Louvel</last-name>
    <headline>Entrepreneur and web APIs expert</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_6X3m4fDpKk7BzKg8k_TA4uupt84czng8kizA4ugmS52Fo-EhEGACn2d8yaZHnlyuFFiKcIifYCWn</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/b4Pg6Pmh9r</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:UdVJ</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=6350866&amp;authToken=UdVJ&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>EepTdYkUad</id>
    <first-name>Céline</first-name>
    <last-name>Louvet</last-name>
    <headline>Developpeuse Java chez SFEIR</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_itf3qR5jBhqMK_gpTzS-qZC1zQis-5SpTvRYqZG2o_cED3sy7N0Ds4nGRH_5lCurGlD0R0y9C944</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/EepTdYkUad</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:FjKA</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=61669153&amp;authToken=FjKA&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>KGgbglBM6e</id>
    <first-name>Teresa</first-name>
    <last-name>Loy</last-name>
    <headline>Communications Specialist at Boeing Commercial Airplanes</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_dtjoY7cYFvYEKWG3WlxJY2br51REt2P3wrdvYDX3s9fsSekT5ND6rSKfwgUvAItDHl0qlE9KSGaK</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/KGgbglBM6e</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:e0fN</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=47330414&amp;authToken=e0fN&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Greater Seattle Area</name>
      <country>
        <code>us</code>
      </country>
    </location>
    <industry>Aviation &amp; Aerospace</industry>
  </person>
  <person>
    <id>xXOitgbY89</id>
    <first-name>Pierre</first-name>
    <last-name>Mage</last-name>
    <headline>Agile Developer and Facilitator</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_QQ0tAdjKZzVRU8FjX3aTAwfpZNS9BiFjXXIDAE0hb-M6ebrgo8fYxowanMDdqX6Ab5juODTnHQ0T</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/xXOitgbY89</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:TcRX</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=27358211&amp;authToken=TcRX&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>LMXEdlkOv9</id>
    <first-name>Amandine</first-name>
    <last-name>Marousez</last-name>
    <headline>Chef de projet AMOA - Product Owner</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_pCD07nJ1VOCfnLvWOLxh7zWK4x5DNQqWO6J87zwDFJljwGidKkj13v0i9tL-cTz5jTf38tvZbga_</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/LMXEdlkOv9</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:UTNM</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=8816048&amp;authToken=UTNM&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Lille Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Internet</industry>
  </person>
  <person>
    <id>NMc9Ug_p_x</id>
    <first-name>Sara</first-name>
    <last-name>Martín de la Fuente</last-name>
    <headline>Junior engineer</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_JqFQXFvY7WJr0MaVUABMXk9G_df80VaVRK1JXkqYy7RKhYJsv-cEEX8KSX7xyjf94cLZIiQXQdsR</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/NMc9Ug_p_x</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:va3v</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=51118657&amp;authToken=va3v&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Brussels Area, Belgium</name>
      <country>
        <code>be</code>
      </country>
    </location>
    <industry>Renewables &amp; Environment</industry>
  </person>
  <person>
    <id>CMz74L5TZI</id>
    <first-name>Emmanuel</first-name>
    <last-name>Mayer</last-name>
    <headline>Senior Manager chez ELCA infomatique SA</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_dCvykoMhyIRQ4q4UbLnfkesfpuuE4-4UwhTikeHriWZsFnfR5k5KoHyO-G2vUzJBHTBCwfs45fxN</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/CMz74L5TZI</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:UtaW</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=88250566&amp;authToken=UtaW&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>IIhtOGN_hw</id>
    <first-name>Sébastien</first-name>
    <last-name>Mazzon</last-name>
    <headline>Ingénieur at Eurogiciel</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_zVjRUCK4IQgO87R8BsaHU3Nzd3Zr82R8BRdkU31k9Xuhxe2hMpDczTbEF74fiIVuqJ0LNQD2gVcH</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/IIhtOGN_hw</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:aMo7</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=12834734&amp;authToken=aMo7&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Nantes Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>3AG_nPpolQ</id>
    <first-name>Ferghal</first-name>
    <last-name>McTaggart</last-name>
    <headline>English Language Teacher at Leicester Square School of English</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_5fq3TIaktOjUwBKVLWnYTegeKRfRwl3VFHhYTeDn7ORINz9sddXDfHMZYv7XHnh9k290mfPkanaT</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/3AG_nPpolQ</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:VEY3</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=141391690&amp;authToken=VEY3&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>London, United Kingdom</name>
      <country>
        <code>gb</code>
      </country>
    </location>
    <industry>Higher Education</industry>
  </person>
  <person>
    <id>private</id>
    <first-name>private</first-name>
    <last-name>private</last-name>
  </person>
  <person>
    <id>cY3oOBCwg-</id>
    <first-name>Zdenek</first-name>
    <last-name>Mikovec</last-name>
    <headline>Researcher at Czech Technical University in Prague</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_SrapvYbAsQhXrXW17qjfvjL0V_-HrbM1fAV_vpQfLQ64fimP39OrRy-3qDtcP8Z0u12GsUNjzOZV</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/cY3oOBCwg-</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:FJZs</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=5290557&amp;authToken=FJZs&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Czech Republic</name>
      <country>
        <code>cz</code>
      </country>
    </location>
    <industry>Research</industry>
  </person>
  <person>
    <id>wyryrhk2ly</id>
    <first-name>Jean-Baptiste</first-name>
    <last-name>Monville</last-name>
    <headline>Chief Product Owner at Adeo Services</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/wyryrhk2ly</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:_DAy</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=57512977&amp;authToken=_DAy&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Lille Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>Gt43zgHqii</id>
    <first-name>Sebastien</first-name>
    <last-name>Morhan</last-name>
    <headline>IT Consultant at Generali</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_mVxsC0kzoMcvhHXcuUDLCgkJoyA5haqc2jwLCgz5zMLv0EiBGp7n2jTwX-ls_dzR7JgkSVFkKpR0</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/Gt43zgHqii</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:tBn9</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=15559875&amp;authToken=tBn9&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Services</industry>
  </person>
  <person>
    <id>WvWaeyYt2b</id>
    <first-name>Audrey</first-name>
    <last-name>Neveu</last-name>
    <headline>Ingénieur d'études et de développement Java / Java EE chez SFEIR</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_Z3_74XHpP3jx4Nxu4G3K4bUglie7qNxuV6Br4bW2m6yxIre2qFP_nFjGxSHKBPY8MGTlchUY_Q5f</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/WvWaeyYt2b</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:Nly3</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=64519180&amp;authToken=Nly3&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>MIRGM3nEhs</id>
    <first-name>Emma</first-name>
    <last-name>Nieto</last-name>
    <headline>JAVA Consultant</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/MIRGM3nEhs</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:LeA3</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=67033557&amp;authToken=LeA3&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>dVtGciMYZh</id>
    <first-name>Lucía</first-name>
    <last-name>Nieto Tejada</last-name>
    <headline>Editora Avid y Final Cut</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_QoM0AdmzUqWU63NjQueuAEmvUAS9oCNjXeS8AE0vkqM6z6_go2W1xowUvxDdW5vAbdU3ODT4AMSc</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/dVtGciMYZh</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:FZtT</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=95882290&amp;authToken=FZtT&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Madrid Area, Spain</name>
      <country>
        <code>es</code>
      </country>
    </location>
    <industry>Broadcast Media</industry>
  </person>
  <person>
    <id>Wud9ahWMGO</id>
    <first-name>Erell</first-name>
    <last-name>OLIVO</last-name>
    <headline>Clinical research assitant</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/Wud9ahWMGO</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:4Kee</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=99889289&amp;authToken=4Kee&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Pharmaceuticals</industry>
  </person>
  <person>
    <id>p_Urd3wxq5</id>
    <first-name>Aurélien</first-name>
    <last-name>Pelletier</last-name>
    <headline>CTO at SFEIR</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_ePvupfvS-PjHr-tio-10pSN_rlZdp-tiotT1pSkPfNuJin5_Xv58tDrgO049xzPfWKBt1onGdmRb</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/p_Urd3wxq5</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:aUS8</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=2005626&amp;authToken=aUS8&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>yN6XYFWK-q</id>
    <first-name>Marcos</first-name>
    <last-name>Perez, PMP</last-name>
    <headline>Manager at ELCA</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_NArAmTtxpxfxr-bbqlACmGktyssxt-bb9PXmmG_T_xm7SnKF4chjhCc7tqV8AzQwv--fT5AF5NOL</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/yN6XYFWK-q</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:BdrL</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=1607731&amp;authToken=BdrL&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>private</id>
    <first-name>private</first-name>
    <last-name>private</last-name>
  </person>
  <person>
    <id>SyREETiNG8</id>
    <first-name>antje</first-name>
    <last-name>peter</last-name>
    <headline>--</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/SyREETiNG8</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:b8b6</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=154122944&amp;authToken=b8b6&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Germany</name>
      <country>
        <code>de</code>
      </country>
    </location>
  </person>
  <person>
    <id>MPPuRDzn3T</id>
    <first-name>Iya</first-name>
    <last-name>Bordyuzhenko</last-name>
    <headline>International &amp; GR Relations Manager в 2018 FIFA World Cup Russia Local Organising Committee</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_uQ-VaJdjuR_oUgXE2FzdasYy2yX6BgvEab65asJ81Vr9eJCoh8G98VSmGlkJqsNQS5rX3jFJR-LI</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/MPPuRDzn3T</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:iya4</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=153656842&amp;authToken=iya4&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Russian Federation</name>
      <country>
        <code>ru</code>
      </country>
    </location>
    <industry>International Affairs</industry>
  </person>
  <person>
    <id>jqLyMqcrcd</id>
    <first-name>Quang-Khai</first-name>
    <last-name>Pham</last-name>
    <headline>Expert conseil - opérations de fonds</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_R7Vm6_fWBeHioFDzRwJg6hEWcSOfECjzMeaA6heqHeo0v6ovBWoCe8pVJiYrd5pJVu4KdbsK_fQj</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/jqLyMqcrcd</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:-tuX</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=81161476&amp;authToken=-tuX&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Montreal, Canada Area</name>
      <country>
        <code>ca</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>aP-PsNdmga</id>
    <first-name>Christophe</first-name>
    <last-name>PHU</last-name>
    <headline>computing engineer at SFEIR</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/aP-PsNdmga</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:GFbw</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=42439272&amp;authToken=GFbw&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Internet</industry>
  </person>
  <person>
    <id>md9yL66Gm-</id>
    <first-name>Charlotte</first-name>
    <last-name>PINEAU</last-name>
    <headline>Sales and Business Development Manager</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_ncgMlb_jWld1tDzp9KmQlX_jwA7rtDzpqvW6lLP3NBUhSWGyVASv05Ff5pffAoqrBqxbYCDhFK7P</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/md9yL66Gm-</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:SkhA</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=15970831&amp;authToken=SkhA&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Brisbane Area, Australia</name>
      <country>
        <code>au</code>
      </country>
    </location>
    <industry>Machinery</industry>
  </person>
  <person>
    <id>lYTXU1ECbQ</id>
    <first-name>Nicolas</first-name>
    <last-name>Rémond</last-name>
    <headline>Software engineer</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_eMnW9EzLCCyvmJmx6gB99db6GGfLu00xoYC99HNqjFRzPUH0XxQ5ZehV2u7Vf4OPWsNBJ76dxvih</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/lYTXU1ECbQ</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:rJ0Q</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=4712798&amp;authToken=rJ0Q&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>oQG_A737Nv</id>
    <first-name>Daniel</first-name>
    <last-name>Rodríguez Postigo</last-name>
    <headline>Software Engineer en ELCA infomatique SA</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/oQG_A737Nv</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:EJEI</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=51940077&amp;authToken=EJEI&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Zürich Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>T8atcZS3mf</id>
    <first-name>Nadia</first-name>
    <last-name>Sol</last-name>
    <headline>Project manager, Product owner</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_R7Vm6_eNzeJDECuzvwZA6howcSOfECjzMmaA6heqHeo0v6ovBWoCe8pVJiYrd5pJVu4KdbnOV61j</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/T8atcZS3mf</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:qUI0</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=24161235&amp;authToken=qUI0&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Lille Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Internet</industry>
  </person>
  <person>
    <id>hrbDP99t9L</id>
    <first-name>Fabrice</first-name>
    <last-name>Sznajderman</last-name>
    <headline>Senior Web Java - Java EE Developer at BNP Paribas Arbitrage</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_mB1VmUzp_pqFPpvw2vr5mJq070Q5Pxvw2KF5m4v_KVtvuRCIGli9hZ3STl6srZNb7NAXTxF7je5O</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/hrbDP99t9L</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:F1fU</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=6019663&amp;authToken=F1fU&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>3t67NpnrqQ</id>
    <first-name>Slim</first-name>
    <last-name>Tebourbi</last-name>
    <headline>Team Leader / Référent Hadoop chez Powerspace</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_uBxCSyNlBg1X1kA6GAyAS05xcRv6PC36aKwjS0v_HOT9u69Qhl7mix3SJvzJr5hoSNgyCZwF5Y8g</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/3t67NpnrqQ</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:q9FK</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=26224191&amp;authToken=q9FK&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>wCHc5UAmWV</id>
    <first-name>Maxime</first-name>
    <last-name>Terrettaz</last-name>
    <headline>Senior Java Developper &amp; Interoperability Specialist</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_Lw2NlIpzIlIs5eQpbmfFlegvIcf9LeFpbu4IlexHq-R6V2ryWSpZ0HI6LM7dQ76rXeaEYfDxAdvH</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/wCHc5UAmWV</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:_pwe</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=15039336&amp;authToken=_pwe&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Montreal, Canada Area</name>
      <country>
        <code>ca</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>jEWQGS1Oig</id>
    <first-name>Axel</first-name>
    <last-name>Tessier</last-name>
    <headline>Engineering Manager at Qualys</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_Oz05kBl0QWc-PuxUOA7VkqAlXE8Clf7UplIVkqchR2NtmwZRt1fWoN_aobhg-HmB09jUwPWydj-U</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/jEWQGS1Oig</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:Vobq</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=1082534&amp;authToken=Vobq&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer &amp; Network Security</industry>
  </person>
  <person>
    <id>CH_lS-l_z_</id>
    <first-name>Sovanneary</first-name>
    <last-name>Than</last-name>
    <headline>Chef de Projet Stockage chez SFR</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/CH_lS-l_z_</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:VGMF</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=37381294&amp;authToken=VGMF&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Services</industry>
  </person>
  <person>
    <id>gipSG31Nie</id>
    <first-name>Thierry</first-name>
    <last-name>TREPIED</last-name>
    <headline>Project Director - SFEIR Groupe</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_WTL7mWwitVp5J1-II5qpmISm-0IwMzPIIQArmIoxDV0UQlkwLbz_hwYlplwNV-tFeCFlTuqrQmYi</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/gipSG31Nie</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:Lt5i</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=43418674&amp;authToken=Lt5i&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>gD1LJIn2mT</id>
    <first-name>Sébastien</first-name>
    <last-name>Treyvaud</last-name>
    <headline>Senior Software Engineer chez SecuTix SA</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_uQ-Va4V-mpBwBOqEu_i5asRp20X6BgvEaX65asJ81Vr9eJCoh8G98VSmGlkJqsNQS5rX3jbajgCI</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/gD1LJIn2mT</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:6ZJl</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=31724189&amp;authToken=6ZJl&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Geneva Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>th-0sJ0b3-</id>
    <first-name>Francky</first-name>
    <last-name>Trichet</last-name>
    <headline>Head at IREALITE</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_Pk_hAc0Ot1ky9-1gAXCPANUArN8AqqigA_BxANg2flN3IKqj0CP2xqdGOVhaB1GltbTOO1ivcnaB</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/th-0sJ0b3-</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:t0y5</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=24022256&amp;authToken=t0y5&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Nantes Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>N5F2CefPCg</id>
    <first-name>Rea</first-name>
    <last-name>Turohan</last-name>
    <headline>Freelancer presso AARCH-MI</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_io443YZHFyQcEmcc3fdw3judIjA9oDncTI2b3j0zqUL6zWTB72Hq7gwRLrldWoBRGdV6asDxqrc1</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/N5F2CefPCg</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:X0lm</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=161200606&amp;authToken=X0lm&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Brescia Area, Italy</name>
      <country>
        <code>it</code>
      </country>
    </location>
    <industry>Architecture &amp; Planning</industry>
  </person>
  <person>
    <id>-VEyNXIAJf</id>
    <first-name>Joseba</first-name>
    <last-name>Urzelai</last-name>
    <headline>Head of Division ECM at ELCA infomatique SA</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_RNyrIkFxMdOCtheIMBx7I66KMIwit_dIJre7I6qTQSx1SFOwBtup5Q87B6IpAkHFVBYaXTQ-oP7-</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/-VEyNXIAJf</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:veT0</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=1714194&amp;authToken=veT0&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Zürich Area, Switzerland</name>
      <country>
        <code>ch</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>UChc6Pp2IN</id>
    <first-name>Orchidée</first-name>
    <last-name>Vaussard</last-name>
    <headline>Gestionnaire Marketing Internet chez Videotron</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_sMe3tGkWBrR_ahlAqOoYt8LLzzg826TA4RyYt8NJoPWK1Cnl9xZDphhcR4jx738jUsE0gFQuNken</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/UChc6Pp2IN</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:M-Tp</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=5843867&amp;authToken=M-Tp&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Montreal, Canada Area</name>
      <country>
        <code>ca</code>
      </country>
    </location>
    <industry>Information Technology and Services</industry>
  </person>
  <person>
    <id>V4jfuAGXDh</id>
    <first-name>Francois</first-name>
    <last-name>Wauquier</last-name>
    <headline>Developer at Sfeir</headline>
    <picture-url>http://m3.licdn.com/mpr/mprx/0_gyqUotveuIKTTgsLgYQHoAnouaqaSgsL0ghXorQnPIhOtJS5AsXBkK-ZC8NAasUdyx95LBNXCap7</picture-url>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/V4jfuAGXDh</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:wo2_</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=34465418&amp;authToken=wo2_&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>Paris Area, France</name>
      <country>
        <code>fr</code>
      </country>
    </location>
    <industry>Computer Software</industry>
  </person>
  <person>
    <id>3PD7tIJqws</id>
    <first-name>Sayaka</first-name>
    <last-name>Yamaichi</last-name>
    <headline>Wimbledon 学生</headline>
    <api-standard-profile-request>
      <url>http://api.linkedin.com/v1/people/3PD7tIJqws</url>
      <headers total="1">
        <http-header>
          <name>x-li-auth-token</name>
          <value>name:haZR</value>
        </http-header>
      </headers>
    </api-standard-profile-request>
    <site-standard-profile-request>
      <url>http://www.linkedin.com/profile?viewProfile=&amp;key=184910677&amp;authToken=haZR&amp;authType=name&amp;trk=api*a196273*s204372*</url>
    </site-standard-profile-request>
    <location>
      <name>London, United Kingdom</name>
      <country>
        <code>gb</code>
      </country>
    </location>
  </person>
</connections>

