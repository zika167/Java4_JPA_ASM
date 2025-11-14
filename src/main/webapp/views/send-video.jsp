
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Send Video to Your Friend</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        .container {
            max-width: 700px;
            margin: 50px auto;
            background-color: white;
            border: 2px solid #d4a574;
            border-radius: 4px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .header {
            background-color: #d4e8d4;
            padding: 15px 20px;
            border-bottom: 2px solid #d4a574;
        }

        .header h2 {
            color: #333;
            font-size: 18px;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .content {
            padding: 30px 20px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 10px;
            color: #333;
            font-size: 16px;
            font-weight: bold;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 2px solid #d4a574;
            border-radius: 4px;
            font-size: 14px;
            font-family: Arial, sans-serif;
        }

        .form-group input:focus {
            outline: none;
            border-color: #ff9800;
            background-color: #fffbf0;
        }

        .button-group {
            display: flex;
            justify-content: flex-end;
            padding: 15px 20px;
            background-color: #f9f9f9;
            border-top: 1px solid #e0e0e0;
        }

        .btn-send {
            background-color: #ff9800;
            color: white;
            border: none;
            padding: 12px 30px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-send:hover {
            background-color: #e68900;
        }

        .btn-send:active {
            background-color: #cc7700;
        }

        /* Info table section */
        .info-section {
            margin-top: 30px;
        }

        .info-table {
            width: 100%;
            border-collapse: collapse;
            border: 2px solid #333;
        }

        .info-table th,
        .info-table td {
            border: 1px solid #333;
            padding: 12px;
            text-align: left;
        }

        .info-table th {
            background-color: #f0f0f0;
            font-weight: bold;
            color: #333;
        }

        .info-table td {
            background-color: white;
        }

        .info-table tr:nth-child(even) td {
            background-color: #fafafa;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>Send video to your friend</h2>
        </div>

        <form method="POST" action="${pageContext.request.contextPath}/send-video">
            <div class="content">
                <div class="form-group">
                    <label for="friendEmail">Your friend's email?</label>
                    <input type="email" id="friendEmail" name="friendEmail" placeholder="Enter your friend's email" required>
                </div>
            </div>

            <div class="button-group">
                <button type="submit" class="btn-send">Send</button>
            </div>
        </form>
    </div>


</body>
</html>
