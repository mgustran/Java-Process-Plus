#!/bin/bash

# Simulating certbot command
#certbot -d "*.example.es"         --server https://acme-v02.api.letsencrypt.org/directory         --preferred-challenges dns certonly         --manual         --agree-tos  --manual-public-ip-logging-ok --force-renewal         --email "mgustran@gmail.com"

echo "Saving debug log to /var/log/letsencrypt/letsencrypt.log"
echo "Requesting a certificate for *.example.es"
echo ""
echo "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"
echo "Please deploy a DNS TXT record under the name:"
echo ""
echo "_acme-challenge.example.es."
echo ""
echo "with the following value:"
echo ""
echo "ar32oHSem2222222222222222222ZkkmNmxVUaJ0"
echo ""
echo "Before continuing, verify the TXT record has been deployed. Depending on the DNS"
echo "provider, this may take some time, from a few seconds to multiple minutes. You can"
echo "check if it has finished deploying with aid of online tools, such as the Google"
echo "Admin Toolbox: https://toolbox.googleapps.com/apps/dig/#TXT/_acme-challenge.netcam5.domotich.es."
echo "Look for one or more bolded line(s) below the line ';ANSWER'. It should show the"
echo "value(s) you've just added."
echo ""
echo "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -"