package org.example;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
public class Main extends TelegramLongPollingBot {
    @SneakyThrows
    public static void main(String[] args) {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(new Main());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private final Map<Long, String> userStates = new HashMap<>();
    @Override
    public String getBotUsername() {
        return "forTestg001_bot";
    }
    @Override
    public String getBotToken() {
        return "8056151603:AAFDA-5hI2dJuqFL09pMiqGtIAzahFtFT8M";
    }
    @Override
    public void onUpdateReceived(Update update) {
        try {
        URL url = new URL("https://cbu.uz/oz/arkhiv-kursov-valyut/json/");
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Gson gson = new Gson();
        Currents[] currents = gson.fromJson(bufferedReader, Currents[].class);
        Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            if (text.equals("/start")||text.equals("/help")) {
                sendMessage.setText("Assalomu alaykum! \uD83D\uDE0A \n" +
                        "Bu bot sizga O'zbekiston Markaziy Bankining valyuta kurslari haqida ma'lumot beradi.\n" +
                        "\n" +
                        "        \uD83D\uDCCB Asosiy komandalar:\n" +
                        "        - /kurs - Barcha valyutalarning kurslarini ko'rish.\n" +
                        "        - /valyuta - Aniq Bir valyutani kursini ko'rish.\n" +
                        "        - /help - Yo'riqnoma.\n" +
                        "\n" +
                        "Qo'shimcha yordam kerak bo'lsa, bizga murojaat qiling: @SHaXa_Jony");
            } else if (text.equals("/valyuta")) {
                sendMessage.setText("•Valyutani Nomini Kiriting!");
            }else if (text.length() == 3) {
                StringBuilder Rates = new StringBuilder();
                for (Currents current : currents) {
                    if (current.getCcy().equals(text)) {
                        Rates.append(" Valyuta: ").append(current.getCcy()).append("\n")
                                .append("\uD83D\uDCB5 Narxi: ").append(current.getRate()).append(" UZS\n")
                                .append("\uD83D\uDCCA O'zgarish: ").append(current.getDiff()).append("\n")
                                .append("\uD83D\uDCC5 Sana: ").append(current.getDate()).append("\n");
                    } else {
                        sendMessage.setText("Notog'ri Buyruq!");
                    }
                }
                sendMessage.setText(Rates.toString());
            } else if (text.equals("/kurs")) {
                StringBuilder Rates = new StringBuilder();
                for (Currents current : currents) {
                    if (current.getCcy().equals("USD")){
                    Rates.append(" Valyuta: ").append(current.getCcy()).append("\n")
                            .append("\uD83D\uDCB5 Narxi: ").append(current.getRate()).append(" UZS\n")
                            .append("\uD83D\uDCCA O'zgarish: ").append(current.getDiff()).append("\n")
                            .append("\uD83D\uDCC5 Sana: ").append(current.getDate()).append("\n")
                            .append("➖➖➖➖➖➖➖➖➖➖\n");
                    }
                    else if (current.getCcy().equals("EUR")) {
                        Rates.append(" Valyuta: ").append(current.getCcy()).append("\n")
                                .append("\uD83D\uDCB5 Narxi: ").append(current.getRate()).append(" UZS\n")
                                .append("\uD83D\uDCCA O'zgarish: ").append(current.getDiff()).append("\n")
                                .append("\uD83D\uDCC5 Sana: ").append(current.getDate()).append("\n")
                                .append("➖➖➖➖➖➖➖➖➖➖\n");
                    }  else if (current.getCcy().equals("RUB")) {
                        Rates.append(" Valyuta: ").append(current.getCcy()).append("\n")
                                .append("\uD83D\uDCB5 Narxi: ").append(current.getRate()).append(" UZS\n")
                                .append("\uD83D\uDCCA O'zgarish: ").append(current.getDiff()).append("\n")
                                .append("\uD83D\uDCC5 Sana: ").append(current.getDate()).append("\n");
                    }
                }
                sendMessage.setText(Rates.toString()+
                        "\n\nBu yerda barcha valyutalar\nNarxi yo'q /valyuta Buyrugidan\nfoydalaning!");
            }  else if (text.equals("USD/USZ")) {
                sendMessage.setText("•USD miqdorini kiritng:");
                userStates.put(chatId, "WAITING_FOR_USD_AMOUNT");
            }else if (text.equals("EUR/USZ")) {
                sendMessage.setText("•EUR miqdorini kiritng:");
                userStates.put(chatId, "WAITING_FOR_EUR_AMOUNT");
            }else if (text.equals("RUB/USZ")) {
                sendMessage.setText("•RUB miqdorini kiritng:");
                userStates.put(chatId, "WAITING_FOR_RUB_AMOUNT");
            }else if (text.equals("USZ/USD")) {
                sendMessage.setText("•USZ miqdorini kiritng:");
                userStates.put(chatId, "WAITING_FOR_USZ/USD_AMOUNT");
            }else if (text.equals("USZ/EUR")) {
                sendMessage.setText("•USZ miqdorini kiritng:");
                userStates.put(chatId, "WAITING_FOR_USZ/EUR_AMOUNT");
            }else if (text.equals("USZ/RUB")) {
                sendMessage.setText("•USZ miqdorini kiritng:");
                userStates.put(chatId, "WAITING_FOR_USZ/RUB_AMOUNT");
            }else if ("WAITING_FOR_USD_AMOUNT".equals(userStates.get(chatId))) {
                try {
                    double dollars = Double.parseDouble(text);
                    double rate = 0;
                    for (Currents current : currents) {
                        if (current.getCcy().equals("USD")) {
                            rate = Double.parseDouble(current.getRate());
                            break;
                        }
                    }
                    double uzbekSom = dollars * rate;
                    sendMessage.setText("\uD83C\uDDFA\uD83C\uDDF8 " + dollars + " USD = \uD83D\uDCB5 " + uzbekSom + " UZS");
                } catch (NumberFormatException e) {
                    sendMessage.setText("⚠️ Iltimos, raqam kiriting!");
                }
                userStates.put(chatId, "");
            } else if ("WAITING_FOR_EUR_AMOUNT".equals(userStates.get(chatId))) {
                try {
                    double eur = Double.parseDouble(text);
                    double rate = 0;
                    for (Currents current : currents) {
                        if (current.getCcy().equals("EUR")) {
                            rate = Double.parseDouble(current.getRate());
                            break;
                        }
                    }
                    double uzbekSom = eur * rate;
                    sendMessage.setText(eur + " EUR = " + uzbekSom + " USZ");
                } catch (NumberFormatException e) {
                    sendMessage.setText("⚠️ Iltimos, raqam kiriting!");
                }
                userStates.put(chatId, "");
            }else if ("WAITING_FOR_RUB_AMOUNT".equals(userStates.get(chatId))) {
                try {
                    double rub = Double.parseDouble(text);
                    double rate = 0;
                    for (Currents current : currents) {
                        if (current.getCcy().equals("RUB")) {
                            rate = Double.parseDouble(current.getRate());
                            break;
                        }
                    }
                    double uzbekSom = rub * rate;
                    sendMessage.setText(rub + " RUB = " + uzbekSom + " USZ");
                } catch (NumberFormatException e) {
                    sendMessage.setText("⚠️ Iltimos, raqam kiriting!");
                }
                userStates.put(chatId, "");
            }else if ("WAITING_FOR_USZ/USD_AMOUNT".equals(userStates.get(chatId))) {
                try {
                    double som = Double.parseDouble(text);
                    double rate = 0;
                    for (Currents current : currents) {
                        if (current.getCcy().equals("USD")) {
                            rate = Double.parseDouble(current.getRate());
                            break;
                        }
                    }
                    double dollar = som / rate;
                    sendMessage.setText("\uD83D\uDCB5 " + som + " UZS = \uD83C\uDDFA\uD83C\uDDF8 " + String.format("%.2f", dollar) + " USD");
                } catch (NumberFormatException e) {
                    sendMessage.setText("⚠️ Iltimos, raqam kiriting!");
                }
                userStates.put(chatId, "");
            }else if ("WAITING_FOR_USZ/EUR_AMOUNT".equals(userStates.get(chatId))) {
                try {
                    double som = Double.parseDouble(text);
                    double rate = 0;
                    for (Currents current : currents) {
                        if (current.getCcy().equals("EUR")) {
                            rate = Double.parseDouble(current.getRate());
                            break;
                        }
                    }
                    double eur = som / rate;
                    sendMessage.setText(som + " USZ = " + eur + " EUR");
                } catch (NumberFormatException e) {
                    sendMessage.setText("⚠️ Iltimos, raqam kiriting!");
                }
                userStates.put(chatId, "");
            }else if ("WAITING_FOR_USZ/RUB_AMOUNT".equals(userStates.get(chatId))) {
                try {
                    double som = Double.parseDouble(text);
                    double rate = 0;
                    for (Currents current : currents) {
                        if (current.getCcy().equals("RUB")) {
                            rate = Double.parseDouble(current.getRate());
                            break;
                        }
                    }
                    double rubl = som / rate;
                    sendMessage.setText(som + " USZ = " + rubl + " RUB");
                } catch (NumberFormatException e) {
                    sendMessage.setText("⚠️ Iltimos, raqam kiriting!");
                }
                userStates.put(chatId, "");
            } else{
                sendMessage.setText("Notog'ri buyruq!");
            }
            sendMessage.setChatId(update.getMessage().getChatId());
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow keyboardRow1 = new KeyboardRow();
            KeyboardRow keyboardRow2 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton("USD/USZ");
            KeyboardButton button2 = new KeyboardButton("EUR/USZ");
            KeyboardButton button3 = new KeyboardButton("RUB/USZ");
            KeyboardButton button4 = new KeyboardButton("USZ/USD");
            KeyboardButton button6 = new KeyboardButton("USZ/EUR");
            KeyboardButton button7 = new KeyboardButton("USZ/RUB");
            keyboardRow1.add(button);
            keyboardRow1.add(button2);
            keyboardRow1.add(button3);
            keyboardRow2.add(button4);
            keyboardRow2.add(button6);
            keyboardRow2.add(button7);
            keyboardRows.add(keyboardRow1);
            keyboardRows.add(keyboardRow2);
            replyKeyboardMarkup.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            execute(sendMessage);
        } catch (TelegramApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}