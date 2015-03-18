/**
 * @author dawson dong
 */

package me.dawson.kisstools.shell;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.dawson.kisstools.KissEnv;
import me.dawson.kisstools.utils.LogUtil;
import me.dawson.kisstools.utils.NumberUtil;

public class ShellUtil {
	public static final String TAG = "ShellTool";

	private static final String COMMAND_SU = "su";
	private static final String COMMAND_SH = "sh";
	private static final String CONTEXT_EXIT = "exit" + KissEnv.LINE_SEPARATOR;
	private static final String COMMANDS_END = "@=-=@";

	private DataOutputStream dataOutputStream = null;
	private Process shellProcess = null;
	private BufferedReader successReader = null;
	private BufferedReader errorReader = null;

	public ShellUtil() {
		//
	}

	public boolean prepare(boolean asRoot) {
		try {
			shellProcess = Runtime.getRuntime().exec(
					asRoot ? COMMAND_SU : COMMAND_SH);
			dataOutputStream = new DataOutputStream(
					shellProcess.getOutputStream());
			successReader = new BufferedReader(new InputStreamReader(
					shellProcess.getInputStream(), "UTF-8"));
			errorReader = new BufferedReader(new InputStreamReader(
					shellProcess.getErrorStream(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public ShellResult execute(String command) {
		List<String> commands = new ArrayList<String>();
		commands.add(command);
		return execute(commands);
	}

	private ShellResult execute(List<String> commands) {
		//
		int resultCode = -1;
		if (commands == null || commands.size() == 0) {
			return new ShellResult(resultCode, null, null);
		}
		commands.add("echo " + COMMANDS_END + " $?");

		String successMsg = null;
		String errorMsg = null;

		try {
			for (String command : commands) {
				if (command == null) {
					continue;
				}
				dataOutputStream.write(command.getBytes());
				dataOutputStream.writeBytes(KissEnv.LINE_SEPARATOR);
				dataOutputStream.flush();
			}

			StringBuilder successBuilder = new StringBuilder();

			String line;
			while ((line = successReader.readLine()) != null) {
				if (line.contains(COMMANDS_END)) {
					break;
				}
				successBuilder.append(line).append(KissEnv.LINE_SEPARATOR);
			}
			String[] fields = line.split(" ");
			if (fields != null && fields.length >= 2 && fields[1] != null) {
				resultCode = NumberUtil.parse(fields[1], resultCode);
			}
			successMsg = successBuilder.toString();

			if (resultCode != 0) {
				StringBuilder errorBuilder = new StringBuilder();
				while (errorReader.ready()
						&& (line = errorReader.readLine()) != null) {
					errorBuilder.append(line).append(KissEnv.LINE_SEPARATOR);
				}
				errorMsg = errorBuilder.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ShellResult(resultCode, successMsg, errorMsg);
	}

	public void exit() {
		try {
			if (dataOutputStream != null) {
				dataOutputStream.writeBytes(CONTEXT_EXIT);
				dataOutputStream.flush();
				dataOutputStream.close();
			}

			if (successReader != null) {
				successReader.close();
				successReader = null;
			}

			if (errorReader != null) {
				errorReader.close();
				errorReader = null;
			}

			if (shellProcess != null) {
				int result = shellProcess.waitFor();
				LogUtil.d(TAG, "exit shell process " + result);
				shellProcess.destroy();
				shellProcess = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
