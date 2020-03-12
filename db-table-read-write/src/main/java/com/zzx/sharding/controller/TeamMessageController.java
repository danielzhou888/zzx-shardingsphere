package com.zzx.sharding.controller;

import com.zzx.sharding.entity.TeamMessage;
import com.zzx.sharding.enums.ResponseEnum;
import com.zzx.sharding.resp.Page;
import com.zzx.sharding.resp.PageResponse;
import com.zzx.sharding.resp.ServiceResponse;
import com.zzx.sharding.service.TeamMessageServiceApi;
import com.zzx.sharding.utils.SnowIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @description: 群消息
 * @author: zhouzhixiang
 * @date: 2019-12-09
 * @company: 叮当快药科技集团有限公司
 **/
@RestController
@RequestMapping("teamMessage")
public class TeamMessageController {

	@Autowired
	private TeamMessageServiceApi teamMessageServiceApi;

	@GetMapping("list")
	public PageResponse list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PageResponse<Page<TeamMessage>> result = new PageResponse<>();
		Page<TeamMessage> page = new Page<>();
		List<TeamMessage> promptLists = teamMessageServiceApi.selectAll();
		result.setCode(ResponseEnum.SUCCESS.getCode());
		result.setMsg(ResponseEnum.SUCCESS.getName());
		page.setList(promptLists);
		page.setTotalRow(promptLists.size());
		page.setPageNumber(1);
		page.setPageSize(1);
		page.setTotalPage(1);
		result.setData(page);
		return result;
	}

	@PostMapping("add")
	public ServiceResponse add(@RequestBody TeamMessage vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResponse result = new ServiceResponse<>();
		vo.setSendAt(new Date().getTime());
		vo.setTeamId(1000000);
		vo.setTeamClientMsgId(UUID.randomUUID().toString());
		vo.setTeamMsgId(Long.valueOf(UUID.randomUUID().toString()));
		this.teamMessageServiceApi.insert(vo);
		result.setCode(ResponseEnum.SUCCESS.getCode());
		result.setMsg(ResponseEnum.SUCCESS.getName());
		return result;
	}

	@GetMapping("delete/{teamMsgId}")
	public ServiceResponse delete(@PathVariable(value = "teamMsgId") long teamMsgId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResponse result = new ServiceResponse<>();
		long success = teamMessageServiceApi.deleteById(teamMsgId);
		if (success > 0) {
			result.setCode(ResponseEnum.SUCCESS.getCode());
			result.setMsg(ResponseEnum.SUCCESS.getName());
		} else {
			result.setCode(ResponseEnum.FAIL.getCode());
			result.setMsg(ResponseEnum.FAIL.getName());
		}
		return result;
	}

	@PostMapping("batchAdd")
	public ServiceResponse batchAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResponse result = new ServiceResponse<>();
		for (int i = 0; i < 20; i++) {
		    TeamMessage vo = new TeamMessage();
			vo.setSendAt(new Date().getTime());
			vo.setTeamId(1001);
			vo.setTeamClientMsgId(UUID.randomUUID().toString());
			vo.setTeamMsgId(SnowIdUtils.uniqueLong());
			//vo.setId(SnowIdUtils.uniqueLong());
			vo.setText("消息内容-"+UUID.randomUUID());
			this.teamMessageServiceApi.insert(vo);
		}
		result.setCode(ResponseEnum.SUCCESS.getCode());
		result.setMsg(ResponseEnum.SUCCESS.getName());
		return result;
	}
}
