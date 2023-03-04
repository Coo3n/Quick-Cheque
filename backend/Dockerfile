FROM python:3.10-bullseye

WORKDIR /var/app

RUN mkdir /var/processors
RUN chown nobody:nogroup /var/processors

COPY requirements.txt .
RUN pip install -r requirements.txt

# For Development
ENV FLASK_ENV=development
ENV FLASK_APP=./quick_check

COPY src/. .
COPY gunicorn.sh .

USER nobody
CMD ["flask", "run", "-h", "0.0.0.0"]

# For Deployment
# ENTRYPOINT ["./gunicorn.sh"]
