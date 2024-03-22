FROM oven/bun:alpine AS builder

WORKDIR /app
COPY package.json .
COPY bun.lockb .
RUN bun install
COPY . .

CMD [ "bun", "run", "build" ]

FROM nginx:alpine-slim

COPY --from=builder /app/dist /usr/share/nginx/html