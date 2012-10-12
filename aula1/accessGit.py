# coding: utf-8

import sys
import json
import requests
import Image
import urllib

import PIL
from PIL import Image

url = 'https://api.github.com/users/{}'.format(sys.argv[1])

r = requests.get(url)
data = json.loads(r.read())

if 'name' in data:
    print 'Nome: ', data['name']
if 'url' in data:
    print 'URL: ', data['url']
if 'location' in data:
    print 'Localização: ', data['location']
if 'email' in data:
    print 'Email: ', data['email']
if 'login' in data:
    print 'Login: ', data['login']
if 'blog' in data:
    print 'Blog: ', data['blog']
if 'avatar_url' in data:
    print 'Avatar: ', data['avatar_url']

basewidth = 300
urllib.urlretrieve(data['avatar_url'],'imagem.png')
img = Image.open('imagem.png')
wpercent = (basewidth / float(img.size[0]))
hsize = int((float(img.size[1]) * float(wpercent)))
img = img.resize((basewidth, hsize), PIL.Image.ANTIALIAS)
img.save('resized_image.png')
Image.open('resized_image.png').show()
