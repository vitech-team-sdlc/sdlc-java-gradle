SECRETNAME=docker-gcr-test
kubectl create secret docker-registry $SECRETNAME \
  --docker-server=https://gcr.io \
  --docker-username=_json_key \
  --docker-email=sdlc@vitechteam.com \
  --docker-password="$(cat key.json)"